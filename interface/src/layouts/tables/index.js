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
import Card from "@mui/material/Card";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";

// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import DataTable from "examples/Tables/DataTable";

// Data
import authorsTableData from "layouts/tables/data/authorsTableData";
import bookingsTableData from "layouts/tables/data/bookingsTableData";
import consultantsTableData from "layouts/tables/data/consultantsTableData";

import { useState, useEffect } from "react";
import { Table,TableBody,TableCell,TableContainer,TableHead} from "@mui/material";


function Tables() {
  // const { columns, rows } = authorsTableData();
  // const { columns: pColumns, rows: pRows } = bookingsTableData();
  // const {columns: CColumns, rows: CRows} = consultantsTableData();

//get users, get consultants, get bookings
//allow editing and updating of users, consultants

  const [users, setUsers] = useState([]);
  const [consultants, setConsultants] = useState([]);
  const [bookings, setBookings] = useState([]);

  const FetchConsultants=async()=>{

    let promise = await fetch("http://localhost:9000/resource-management-service/consultants",{method: "GET"});
    let result = await promise.json();
    

    console.log(result);
    console.log(JSON.stringify(result));
    setConsultants(result);
    
   

   }

   useEffect(() => {
    FetchConsultants();
  }, []);

  return (
    
    <DashboardLayout>
      <DashboardNavbar />
     
      
    </DashboardLayout>
  );
}

export default Tables;
