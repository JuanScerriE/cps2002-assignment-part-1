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
import { DataGrid } from '@mui/x-data-grid';
// Data
import authorsTableData from "layouts/tables/data/authorsTableData";
import bookingsTableData from "layouts/tables/data/bookingsTableData";
import consultantsTableData from "layouts/tables/data/consultantsTableData";

import { useState, useEffect } from "react";
import { Table,TableBody,TableCell,TableContainer,TableHead,Paper,TableRow} from "@mui/material";


function Tables() {
  // const { columns, rows } = authorsTableData();
  // const { columns: pColumns, rows: pRows } = bookingsTableData();
  // const {columns: CColumns, rows: CRows} = consultantsTableData();

//get users, get consultants, get bookings
//allow editing and updating of users, consultants

  const [users, setUsers] = useState([]);
  const [consultants, setConsultants] = useState([]);
  const [selectedConsultant, setSelectedConsultant] = useState(
  
  );

  const [update_name, setUpdateName] = useState('');
  const [update_type, setUpdateType] = useState('');
  const [update_speciality, setUpdateSpeciality] = useState('');
  const [update_rate, setUpdateRate] = useState(0);


 
  const [bookings, setBookings] = useState([]);
  
  const FetchConsultants=async()=>{

    let promise = await fetch("http://localhost:9000/resource-management-service/consultants",{method: "GET"});
    let result = await promise.json();
    

    console.log(result);
    console.log(JSON.stringify(result));
    setConsultants(result);
    
   

   }

   const handleDeleteConsultant = async () => {
    console.log(selectedConsultant);
    let promise = await fetch(`http://localhost:9000/resource-management-service/delete/${selectedConsultant.uuid}`, {  method: "DELETE"});
    let result = await promise.json();
    console.log(result);
    FetchConsultants();
  }

  const handleUpdateConsultant = async () => {
    console.log('update');
    let promise = await fetch("http://localhost:9000/resource-management-service/update_consultant", {  method: "POST",headers: {
      'Content-Type': 'application/json',

  }, body: JSON.stringify({value:{uuid:selectedConsultant.uuid,name: update_name, type: update_type, speciality: update_speciality, rate: update_rate}})});
    let result = await promise.json();
    console.log(result);
    FetchConsultants();
   
  }


   useEffect(() => {
    FetchConsultants();
  }, []);

  const columns = [
    { field: 'uuid', headerName: 'ID', width: 160 },
    { field: 'name', headerName: 'Name', width: 100 },
    { field: 'type', headerName: 'Type', width: 100 },
    { field: 'speciality', headerName: 'Speciality', width: 100 },
    { field: 'rate', headerName: 'Rate', width: 100 },
  ]
  let rows = consultants;
  return (
    
    <DashboardLayout>
      <DashboardNavbar />
      CONSULTANTS TABLE
      {/* <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow >
            <TableCell>Name</TableCell>
            <TableCell align="right">uuid</TableCell>
            <TableCell align="right">type</TableCell>
            <TableCell align="right">speciality</TableCell>
            <TableCell align="right">rate</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {consultants.map((row) => (
            <TableRow
              key={row.name}
              
            >
              <TableCell component="th" scope="row">
                {row.name}
              </TableCell>
              <TableCell >{row.uuid}</TableCell>
              <TableCell >{row.type}</TableCell>
              <TableCell >{row.speciality}</TableCell>
              <TableCell>{row.rate}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer> */}
 <DataGrid
 sx={{minHeight: 250}}
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
        getRowId={(row) => row.uuid}
        onSelectionModelChange={(newSelection) => {
          console.log(newSelection);
          const selectedIds = newSelection;
          const selectedRowData = rows.filter((row) =>
            selectedIds.includes(row.uuid)
          );
          console.log(selectedRowData[0]);
          setSelectedConsultant(selectedRowData[0]);
          setUpdateName(selectedRowData[0].name);
          setUpdateType(selectedRowData[0].type);
          setUpdateSpeciality(selectedRowData[0].speciality);
          setUpdateRate(selectedRowData[0].rate);

        }}
      
          
      />
      {
        selectedConsultant && <div style={{display:'flex',width:'100%',flexDirection:"row"}}>
          <h3 onClick={handleDeleteConsultant} style={{cursor:'pointer'}}>Delete</h3>
           <div style={{display:'flex',flexDirection:'column',marginLeft:'5%'}}>
            <h3>Update</h3>
            <input type="text" placeholder={selectedConsultant.name} value={update_name} onChange={(e)=>{setUpdateName(e.target.value)}} style={{marginTop:'2%'}} />
            <input type="text" placeholder={selectedConsultant.type} value={update_type} onChange={(e)=>{setUpdateType(e.target.value)}} style={{marginTop:'2%'}}/>
            <input type="text" placeholder={selectedConsultant.speciality} value={update_speciality} onChange={(e)=>{setUpdateSpeciality(e.target.value)}} style={{marginTop:'2%'}}/>
            <input type="number" placeholder={selectedConsultant.rate} value={update_rate} onChange={(e)=>{setUpdateRate(e.target.value)}} style={{marginTop:'2%'}}/>
             
             
              
            <button onClick={handleUpdateConsultant} style={{marginTop:'2%'}}>Submit</button>

           </div>
        </div>

      }
    </DashboardLayout>
  );
}

export default Tables;
