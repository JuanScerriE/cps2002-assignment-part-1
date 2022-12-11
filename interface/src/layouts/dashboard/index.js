/**
=========================================================
* Material Dashboard 2 React - v2.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2022 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

// @mui material components
import Grid from "@mui/material/Grid"; 
import * as React from "react";
// Material Dashboard 2 React components
import MDBox from "components/MDBox";

// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import ReportsBarChart from "examples/Charts/BarCharts/ReportsBarChart";
import ReportsLineChart from "examples/Charts/LineCharts/ReportsLineChart";
import ComplexStatisticsCard from "examples/Cards/StatisticsCards/ComplexStatisticsCard";
import { TextField,Fab } from "@mui/material";
import MDSnackbar from "../../components/MDSnackbar";
// Data
import reportsBarChartData from "layouts/dashboard/data/reportsBarChartData";
import reportsLineChartData from "layouts/dashboard/data/reportsLineChartData";

// Dashboard components
import Projects from "layouts/dashboard/components/Projects";
import OrdersOverview from "layouts/dashboard/components/OrdersOverview";

function Dashboard() {
  const { sales, tasks } = reportsLineChartData;
  const [userName, setUserName] = React.useState("");
  const [preference, setPreference] = React.useState("");
  const [consultantName, setConsultantName] = React.useState("");
  const [type, setType] = React.useState("");
  const [speciality, setSpeciality] = React.useState("");
  const [rate, setRate] = React.useState(0);

  const [snackbar, setSnackbar] = React.useState({
    open: false,
    message: "",
    color: "success",
  });

  const handleUserNameChange = (event) => {
    setUserName(event.target.value);
  };
  const handlePreferenceChange = (event) => {
    setPreference(event.target.value);
  };
  const handleConsultantNameChange = (event) => {
    setConsultantName(event.target.value);
  };
  const handleTypeChange = (event) => {
    setType(event.target.value);
  };
  const handleSpecialityChange = (event) => {
    setSpeciality(event.target.value);
  };
  const handleRateChange = (event) => {
    setRate(event.target.value);
  };

  const handleCreateUser = async() => {
     const user={
       name: userName,
       preference: preference,
     }
  };

  const handleCreateConsultant =async () => {

    const consultant={
      value:{

      uuid:null,
      name: consultantName,
      type: type,
      speciality: speciality,
      rate: rate
      
    }
    }

      console.log(JSON.stringify(consultant));
   
   await fetch("http://localhost:9000/resource-management-service/new_consultant",{
      method: "POST",
      headers: {
      'Content-Type': 'application/json',
     
    },
      body: JSON.stringify(consultant),
      mode: 'no-cors',
   }).then(async(res)=>{
    
    await res.json().then((res)=>{

      console.log(res)
      setSnackbar({
        open: true,
        content: `Consultant ${res.body} Created Successfully`,
        color: "success",
      
      })})}).catch((err)=>{setSnackbar({
        open: true,
        title: `Consultant ${err} Creation Failed`,
        color: "error",
          
        });
      console.log(err.message);
      }
        );
  };



  return (
    <DashboardLayout>
      <MDSnackbar
        open={snackbar.open}
        message={snackbar.message}
        color={snackbar.color}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
      />
      <DashboardNavbar />
      <MDBox py={3}>
         CREATE USER
        <Grid container spacing={3} direction={'column'}  sx={{marginBottom:'5%'}}>
          <Grid item xs={12} md={6}>
                   
            <TextField   label=" Name" variant="outlined" fullWidth value={userName} onChange={(e)=>handleUserNameChange(e)}/>
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Preference" variant="outlined" fullWidth value={preference} onChange={(e)=>handlePreferenceChange(e)}/>
          </Grid>
          <Fab
          onClick={handleCreateUser}
          variant="extended"
          sx={
            {
                  color: "#fff",
                  width: 300,
                  height: 35,
                  borderRadius: 2,
                  alignSelf: "center",
                  marginTop: "5%",
                  fontSize: "1rem",
                  backgroundColor: "#000",
                  transition:'all 0.3s ease-in-out',
                  ": hover": {
                    backgroundColor: "#FFF",
                    color: "#000",
                    border: "1px solid black",
                    transform:'scale(1.05)'
                  },
                }
             
          }
         
        >
          Submit
        </Fab>
       </Grid>
       CREATE CONSULTANT
       <Grid container spacing={3} direction={'column'} sx={{marginBottom:'5%'}}> 
          
          <Grid item xs={12} md={6}>
                   
            <TextField   label=" Name" variant="outlined" fullWidth value={consultantName} onChange={(e)=>handleConsultantNameChange(e)} />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Type" variant="outlined" fullWidth value={type} onChange={(e)=>handleTypeChange(e)} />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Speciality" variant="outlined" fullWidth value={speciality} onChange={(e)=>handleSpecialityChange(e)} />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Rate" variant="outlined" fullWidth type={'number'}  value={rate} onChange={(e)=>handleRateChange(e)}/>
          </Grid>
          <Fab
        onClick={()=>handleCreateConsultant()}
       variant="extended"
       sx={
         {
               color: "#fff",
               width: 300,
               height: 35,
               borderRadius: 2,
               alignSelf: "center",
               marginTop: "5%",
               fontSize: "1rem",
               backgroundColor: "#000",
               transition:'all 0.3s ease-in-out',
               ": hover": {
                 backgroundColor: "#FFF",
                 color: "#000",
                 border: "1px solid black",
                 transform:'scale(1.05)'
               },
             }
          
       }
      
     >
       Submit
     </Fab>
       </Grid>
       {/* DELETE USER
        <Grid container spacing={3} direction={'column'}  sx={{marginBottom:'5%'}}>
          <Grid item xs={12} md={6}>
                   
            <TextField   label="Id" variant="outlined" fullWidth />
          </Grid>
         
          <Fab
       
          variant="extended"
          sx={
            {
                  color: "#fff",
                  width: 300,
                  height: 35,
                  borderRadius: 2,
                  alignSelf: "center",
                  marginTop: "5%",
                  fontSize: "1rem",
                  backgroundColor: "#000",
                  transition:'all 0.3s ease-in-out',
                  ": hover": {
                    backgroundColor: "#FFF",
                    color: "#000",
                    border: "1px solid black",
                    transform:'scale(1.05)'
                  },
                }
             
          }
         
        >
          Delete
        </Fab>
       </Grid> */}
      </MDBox>
     
    </DashboardLayout>
  );
}

export default Dashboard;
