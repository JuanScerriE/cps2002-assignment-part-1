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

// Data
import reportsBarChartData from "layouts/dashboard/data/reportsBarChartData";
import reportsLineChartData from "layouts/dashboard/data/reportsLineChartData";

// Dashboard components
import Projects from "layouts/dashboard/components/Projects";
import OrdersOverview from "layouts/dashboard/components/OrdersOverview";

function Dashboard() {
  const { sales, tasks } = reportsLineChartData;

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox py={3}>
         CREATE USER
        <Grid container spacing={3} direction={'column'}  sx={{marginBottom:'5%'}}>
          <Grid item xs={12} md={6}>
                   
            <TextField   label=" Name" variant="outlined" fullWidth />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Preference" variant="outlined" fullWidth />
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
          Submit
        </Fab>
       </Grid>
       CREATE CONSULTANT
       <Grid container spacing={3} direction={'column'} sx={{marginBottom:'5%'}}> 
          
          <Grid item xs={12} md={6}>
                   
            <TextField   label=" Name" variant="outlined" fullWidth />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Type" variant="outlined" fullWidth />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Speciality" variant="outlined" fullWidth />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField   label="Rate" variant="outlined" fullWidth />
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
       Submit
     </Fab>
       </Grid>
       DELETE USER
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
       </Grid>
      </MDBox>
     
    </DashboardLayout>
  );
}

export default Dashboard;
