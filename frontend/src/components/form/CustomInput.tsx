import { FormControl, Input, InputLabel, TextField, Typography } from '@mui/material'
import { ErrorMessage, useField } from 'formik'

export default function CustomInput({...props}: any) {
    const [field, meta] = useField(props);
    const {name} = props 
    return (
    <>
        <FormControl>
            <TextField
                {...field}
                {...props}
            />
            <ErrorMessage name={name} >
                {()=> <Typography variant='caption' color='error'>{meta.error}</Typography>}
            </ErrorMessage>
        </FormControl>
    </>
  )
}
