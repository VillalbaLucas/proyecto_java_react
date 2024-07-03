import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import CssBaseline from "@mui/material/CssBaseline";
import IconButton from "@mui/material/IconButton";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { Link, useNavigate } from "react-router-dom";

export default function NavBar() {
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const isLoggedIn = localStorage.getItem("userId");
  const navigate = useNavigate();

  const handleDrawerToggle = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const handleLogout = () => {
    localStorage.removeItem("userId"); // Eliminar el valor del localStorage al hacer logout
    navigate("/account/login"); // Redirigir al usuario a la página de login
  };

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar component="nav">
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: "none" } }}
          ></IconButton>
          <Typography
            variant="h6"
            component="div"
            sx={{ flexGrow: 1, display: { xs: "none", sm: "block" } }}
          >
            AlMedin
          </Typography>
          <Box sx={{ display: { xs: "none", sm: "block" } }}>
            {isLoggedIn ? (
              <>
                <Link
                  to={"/"}
                  onClick={handleLogout}
                  style={{ textDecoration: "none" }}
                >
                  <Button variant="contained" color="secondary">
                    Cerrar sesión
                  </Button>
                </Link>
              </>
            ) : (
              <>
                <Link to={"/account/login"} style={{ textDecoration: "none" }}>
                  <Button variant="contained" color="secondary">
                    Iniciar sesión
                  </Button>
                </Link>
              </>
            )}
            <Link to={"/home"}>
              <Button sx={{ color: "#fff" }}>Home</Button>
            </Link>
            <Link to={"/medicals"}>
              <Button sx={{ color: "#fff" }}>Carta especialistas</Button>
            </Link>
            <Link to={"/my-appointments"}>
              <Button sx={{ color: "#fff" }}>Mis turnos</Button>
            </Link>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
