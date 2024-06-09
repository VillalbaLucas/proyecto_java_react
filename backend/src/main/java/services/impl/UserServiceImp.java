package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import Exception.UserException;
import dto.request.UserDtoLogin;
import dto.request.UserDtoRegister;
import dto.request.UserDtoUpdate;
import dto.response.FullUserDto;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import mapper.UserMapper;
import models.User;
import repositories.UserRepository;
import services.UserService;

@ApplicationScoped
public class UserServiceImp implements UserService {

    @Inject
    private UserRepository userRepo;

    @Inject
    Validator validator;

    @Transactional
    @Override
    public void registerAndSave(UserDtoRegister userRegister) throws Exception {
        validateUser(userRegister);
        Optional<User> userEntity = userRepo.findByEmail(userRegister.email());

        if (userEntity.isPresent())
            throw new UserException("User with email [" + userRegister.email() + "] is already exist!");

        User userToPersist = UserMapper.dtoToUser(userRegister);
        userToPersist.setPassword(BcryptUtil.bcryptHash(userToPersist.getPassword()));
        userRepo.persist(userToPersist);
    }
    
    @Override
    public FullUserDto loginUser(UserDtoLogin userLogin) throws Exception {
        validateUser(userLogin);

        User userEntity = userRepo.findByEmail(userLogin.email()).orElseThrow(()-> new Exception("Incorrect email or passwords"));
        if(!BcryptUtil.matches(userLogin.password(), userEntity.getPassword()))
            throw new Exception("Incorrect email or passwords");

        return UserMapper.userToDto(userEntity);
    }

    @Override
    public List<FullUserDto> findAll() {
        return userRepo.findAll().project(FullUserDto.class).list();
    }

    @Override
    public User findUserById(UUID id) throws Exception {
        return userRepo.findByIdOptional(id).orElseThrow(() -> new Exception("User not exist with id [ " + id + "]"));
    }

    @Transactional
    @Override
    public void updateUser(UUID id, UserDtoUpdate userUpdate) throws Exception {
        User userEntity = findUserById(id);
        
        if(userUpdate.email() != null)
            userEntity.setEmail(userUpdate.email());
        if(userUpdate.name() != null)
            userEntity.setName(userUpdate.name());
        if(userUpdate.lastname() != null)
            userEntity.setLastname(userUpdate.lastname());
        if(userUpdate.dni() != null)
            userEntity.setDni(userUpdate.dni());
        if(userUpdate.photo() != null)
            userEntity.setPhoto(userUpdate.photo());
        if(userUpdate.birthDate() != null)
            userEntity.setBirthDate(userUpdate.birthDate());
        if(userUpdate.password() != null){
            validateUser(userUpdate);
            userEntity.setPassword(BcryptUtil.bcryptHash(userUpdate.password()));
        }   
    }

    // valida los campos del usuario, lanza una Exception con los campos incorrectos
    private void validateUser(Object objUser) throws Exception {
        var contrains = validator.validate(objUser);
        if (!contrains.isEmpty()) {
            StringBuilder errorsMessage = new StringBuilder();
            contrains.stream()
                    .forEach(c -> errorsMessage.append(c.getMessageTemplate()).append(", "));
            throw new Exception(errorsMessage.toString());
        }
    }

}
