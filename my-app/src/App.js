import React, {useState} from 'react'
import {Navbar, NavbarBrand, Nav, NavItem, NavLink, Collapse, NavbarToggler} from 'reactstrap';//importing react-strap components
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import MyHome from "./components/Main";
import Certificates from "./components/Main";
import Registration from "./components/Registration";
//importing routing elements
//Styling the Links
const Links = {
    color: "palevioletred",
    fontSize: "2.5rem",
    margin: "2rem 0",
    fontWeight: "600",
}//Home page function
function Home() {
    return <h1 className="text-center display-3 text-primary">This is Home Page</h1>
}//About page function
function About() {
    return <h1 className="text-center display-3 text-success">This is About Page</h1>
}//Contact page function
function Contact() {
    return <h1 className="text-center display-3 text-danger">This is Contact Page</h1>
}

function ReactStrap() {
    const [toggle, setToggle] = useState(false);
    const isToggle = () => setToggle(!toggle);
    return (
        <div>
            <Router>
                <Navbar dark color="faded" className="text-dark" style={{backgroundColor: "#1F2833"}}>
                    <NavbarBrand style={{color: "peachpuff", fontSize: "3rem"}}>
                        Coder Academy
                    </NavbarBrand>
                    <NavbarToggler onClick={isToggle}/> <Collapse isOpen={toggle} navbar>
                    <Nav navbar className="text-center"> <NavItem>
                        <NavLink style={Links}>
                            <Link to="/">Home</Link>
                        </NavLink>
                    </NavItem><NavItem>
                        <NavLink style={Links}>
                            <Link to="/about">About</Link>
                        </NavLink>
                    </NavItem>
                        <NavItem>
                            <NavLink style={Links}>
                                <Link to="/certificates">Certificates</Link>
                            </NavLink>
                        </NavItem>
                    </Nav>
                </Collapse>
                </Navbar>
                <Routes>
                    <Route exact path="/" element={<Home/>}/>
                    <Route path="/about" element={<Registration/>}/>
                    <Route path="/certificates" element={<Certificates/>}/>
                </Routes>
            </Router>
        </div>
    )
}

export default ReactStrap