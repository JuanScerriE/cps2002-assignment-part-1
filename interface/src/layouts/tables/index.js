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
import * as React from "react";
import dayjs from 'dayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import DataTable from "examples/Tables/DataTable";
import { DataGrid } from '@mui/x-data-grid';
import MDSnackbar from "../../components/MDSnackbar";
// Data
import authorsTableData from "layouts/tables/data/authorsTableData";
import bookingsTableData from "layouts/tables/data/bookingsTableData";
import consultantsTableData from "layouts/tables/data/consultantsTableData";
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { useState, useEffect } from "react";
import { Table,TableBody,TableCell,TableContainer,TableHead,Paper,TableRow,TextField} from "@mui/material";
import { CommentsDisabledOutlined } from "@mui/icons-material";


function Tables() {
  // const { columns, rows } = authorsTableData();
  // const { columns: pColumns, rows: pRows } = bookingsTableData();
  // const {columns: CColumns, rows: CRows} = consultantsTableData();

//get users, get consultants, get bookings
//allow editing and updating of users, consultants

  const [users, setUsers] = useState([]);
  const [consultants, setConsultants] = useState([]);
  const [selectedConsultant, setSelectedConsultant] = useState();
  const [selectedUser, setSelectedUser] = useState();
  const [selectedBooking, setSelectedBooking] = useState();

  const [update_user_name, setUpdateUserName] = useState('');
  const [update_user_preference, setUserPreference] = useState('');

  const [update_name, setUpdateName] = useState('');
  const [update_type, setUpdateType] = useState('');
  const [update_speciality, setUpdateSpeciality] = useState('');
  const [update_rate, setUpdateRate] = useState(0);

  const [update_booking_date, setUpdateBookingDate] = useState('');
  const [update_booking_time, setUpdateBookingTime] = useState('');
  const [update_booking_consultant, setUpdateBookingConsultant] = useState('');
  const [update_booking_user, setUpdateBookingUser] = useState('');
  const [snackbar, setSnackbar] = React.useState({
    open: false,
    message: "",
    color: "success",
  });
  const [search,setSearch]=useState('');
  const [search_user,setSearchUser]=useState('');
  


  const [value, setValue] = React.useState(dayjs('2014-08-18T21:11:54'));
  const [hours,setHours]=useState(0);

  const handleChange = (newValue) => {
    setValue(newValue);
  };
 
  const [bookings, setBookings] = useState([]);
  
  //cunsultants-service
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
   
    let promise = await fetch("http://localhost:9000/resource-management-service/update_consultant", {  method: "POST",headers: {
      'Content-Type': 'application/json',
      
  }, body: JSON.stringify({value:{uuid:selectedConsultant.uuid,name: update_name, type: update_type, speciality: update_speciality, rate: update_rate}})});
    let result = await promise.json();
    console.log(result);
    FetchConsultants();
   
  }
  
  //users-service
  const FetchUsers=async()=>{
    let promise = await fetch("http://localhost:9000/customer-management-service/get-all",{method: "GET"});
    let result = await promise.json();
    console.log(result);
    console.log(JSON.stringify(result));
    setUsers(result);
  }

  const handleUpdateUser = async () => {
    
    let promise = await fetch("http://localhost:9000/customer-management-service/update", {  method: "PUT",headers: {
      'Content-Type': 'application/json',
      
  }, body: JSON.stringify({uuid:selectedUser.uuid,name: update_user_name,specialityPreference: update_user_preference})});
  
    FetchUsers();

  }
  
  const handleDeleteUser = async () => {
    console.log(selectedUser);
    fetch(`http://localhost:9000/customer-management-service/delete?uuid=${selectedUser.uuid}`, {  method: "DELETE"})
    .then(()=>
      setSelectedUser(null),
      FetchUsers()
    ).catch((err)=>{
      console.log(err);
    })
    
  }

  //booking services
  const FetchBookings=async()=>{
    let promise = await fetch("http://localhost:9000/timetabling-service/get-all",{method: "GET"});
    let result = await promise.json();
    console.log(result);
    console.log(JSON.stringify(result));
    
    //change start and end dates in result to string
    result.forEach((booking)=>{
      booking.start=booking.start.toString();
    
      booking.end=booking.end.toString();
      console.log(booking);
    })

    setBookings(result);
  }

  const handleDeleteBooking = async () => {
    console.log(selectedBooking);
    fetch(`http://localhost:9000/timetabling-service/delete?uuid=${selectedBooking.uuid}`, {  method: "DELETE"})
    .then(async(res)=>{
      setSelectedBooking(null);
     const x= await res.json();

     console.log(x);
      
      FetchBookings();
    }).catch((err)=>{
      console.log(err);
    });
  }

  const handleSearchConsultant = async () => {
    console.log(search);
    let promise = await fetch(`http://localhost:9000/resource-management-service/getallconsultants/${search}`,{method: "GET"});
    let result = await promise.json();
    console.log(result);
    console.log(JSON.stringify(result));
    setConsultants(result);
  }
  const handleSearchUser = async () => {
    let promise = await fetch(`http://localhost:9000/customer-management-service/get-all-by-preference?specialityPreference=${search_user}`,{method: "GET"});
    let result = await promise.json();
    console.log(result);
    console.log(JSON.stringify(result));
    setUsers(result);
  }

 
 

  const handleBookConsultant = async () => {
   const startDate = value.toISOString();
   //end date is start date + hours
    const endDate = value.add(hours, 'hours').toISOString();
   
    //remove .000Z from end date and start date
     const start = startDate.substring(0, startDate.length - 5);
    const end = endDate.substring(0, endDate.length - 5);


    const booking ={
      consultantUuid: selectedConsultant.uuid,
      customerUuid: selectedUser.uuid,
      start: start,
      end: end
    }
    console.log(JSON.stringify(booking));
    let promise = await fetch("http://localhost:9000/timetabling-service/create", {  method: "POST",headers: {
      'Content-Type': 'application/json',
  }, body: JSON.stringify(booking)});
    let result = await promise.json();
    console.log(result);
    //snackbar showing result
    
    setSnackbar({
      open: true,
      message: `User ${result["uuid"]} Creation Successfully`,
      color: "success",
  });

    FetchBookings();

  }

   useEffect(() => {
    FetchConsultants();
    FetchUsers();
    FetchBookings();
  }, [selectedConsultant, selectedUser,selectedBooking]);

  const columns = [
    { field: 'uuid', headerName: 'ID', width: 350 },
    { field: 'name', headerName: 'Name', width: 100 },
    { field: 'type', headerName: 'Type', width: 100 },
    { field: 'speciality', headerName: 'Speciality', width: 250 },
    { field: 'rate', headerName: 'Rate', width: 100 },
  ]
  const user_columns = [
    { field: 'uuid', headerName: 'ID', width: 350 },
    { field: 'name', headerName: 'Name', width: 150 },
    { field: 'specialityPreference', headerName: 'Preference', width: 250 },
  ]
  const booking_columns = [
    { field: 'uuid', headerName: 'ID', width: 350 },
    { field: 'consultantUuid', headerName: 'Consultant ID', width: 150 },
    { field: 'customerUuid', headerName: 'Customer ID', width: 150 },
    { field: 'start', headerName: 'Start Date', width: 200 },
    { field: 'end', headerName: 'End Date', width: 200 },
  ]
  let booking_rows = bookings;
  let user_rows = users;
  let rows = consultants;


  return (
    
    <DashboardLayout>
        <MDSnackbar
          open={snackbar.open}
          title={snackbar.message}
          color={snackbar.color}
          onClose={() => setSnackbar({ ...snackbar, open: false })}
      />
      
      <div style={{display:'flex',flexDirection:'row',marginTop:'3%',marginBottom:'2%'}}>
      <TextField
        sx={{ width: 300 }}
        label="Search Speciality"
        variant="outlined"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
    
      <button
        variant="contained"
        onClick={handleSearchConsultant}
        sx={{ ml: 2 }}
      >
        Search
      </button>
      </div>

      CONSULTANTS TABLE
     
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
          if(selectedRowData[0]!==undefined && selectedRowData[0]!==null){
          setUpdateName(selectedRowData[0].name);
          setUpdateType(selectedRowData[0].type);
          setUpdateSpeciality(selectedRowData[0].speciality);
          setUpdateRate(selectedRowData[0].rate);
          }

        }}
      
          
      />
      {
        selectedConsultant && !selectedUser &&
        <div style={{display:'flex',width:'100%',flexDirection:"row"}}>
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
        <div style={{display:'flex',flexDirection:'row',marginTop:'3%',marginBottom:'2%'}}>
      <TextField
        sx={{ width: 300 }}
        label="Search Preference"
        variant="outlined"
        value={search_user}
        onChange={(e) => setSearchUser(e.target.value)}
      />
    
      <button
        variant="contained"
        onClick={handleSearchUser}
        sx={{ ml: 2 }}
      >
        Search
      </button>
      </div>

      USERS TABLE
      <DataGrid
 sx={{minHeight: 270}}
        rows={user_rows}
        columns={ user_columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
        getRowId={(row) => row.uuid}
        onSelectionModelChange={(newSelection) => {
          console.log(newSelection);
          const selectedIds = newSelection;
          const selectedRowData = user_rows.filter((row) =>
            selectedIds.includes(row.uuid)
          );
          console.log('here');
          console.log(selectedRowData);
          console.log(selectedRowData[0]);
          setSelectedUser(selectedRowData[0]);
          if(selectedRowData[0]!==undefined && selectedRowData[0]!==null){
         
          setUpdateUserName(selectedRowData[0].name);
          setUserPreference(selectedRowData[0].specialityPreference);
          }

        }}
      
          
      />
      {
        selectedUser && <div style={{display:'flex',width:'100%',flexDirection:"row",marginBottom:'7%'}}>
          <h3 onClick={handleDeleteUser} style={{cursor:'pointer'}}>Delete</h3>
           <div style={{display:'flex',flexDirection:'column',marginLeft:'5%'}}>
            <h3>Update</h3>
            <input type="text" placeholder={selectedUser.name} value={update_user_name} onChange={(e)=>{setUpdateUserName(e.target.value)}} style={{marginTop:'2%'}} />
            <input type="text" placeholder={selectedUser.specialityPreference} value={update_user_preference} onChange={(e)=>{setUserPreference(e.target.value)}} style={{marginTop:'2%'}}/>
           
            <button onClick={handleUpdateUser} style={{marginTop:'2%'}}>Submit</button>

           </div>
          
            <div style={{display:'flex',flexDirection:'column',marginLeft:'5%'}}>
            <h3 style={{marginBottom:'2%'}}>Book Consultant</h3>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateTimePicker
          label="Date&Time picker"
          value={value}
          onChange={handleChange}
          renderInput={(params) => <TextField {...params} />}
       
        />
        </LocalizationProvider>
            <input type="number" placeholder={"hours"} value={hours} onChange={(e)=>{setHours(e.target.value)}} style={{marginTop:'4%'}}/>

            <button onClick={handleBookConsultant} style={{marginTop:'2%'}}>Submit</button>
            </div>

        </div>

      }
 <h3 style={{marginTop:'10%'}}>BOOKING TABLE</h3>
<DataGrid
 sx={{minHeight: 250}}
        rows={booking_rows}
        columns={booking_columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
        getRowId={(row) => row.uuid}
        onSelectionModelChange={(newSelection) => {
          console.log(newSelection);
          const selectedIds = newSelection;
          const selectedRowData = booking_rows.filter((row) =>
            selectedIds.includes(row.uuid)
          );
          console.log(selectedRowData[0]);
          setSelectedBooking(selectedRowData[0]);
          if(selectedRowData[0]!==undefined && selectedRowData[0]!==null){
           
            setUpdateBookingDate(selectedRowData[0].startDate);
            
            setUpdateBookingConsultant(selectedRowData[0].consultantUuid);
            setUpdateBookingUser(selectedRowData[0].customerUuid);
          }

        }}
      
          
      />
      {
        selectedBooking  &&
        <div style={{display:'flex',width:'100%',flexDirection:"row"}}>
          <h3 onClick={handleDeleteBooking} style={{cursor:'pointer'}}>Delete</h3>
          

          
        </div>

      }
    </DashboardLayout>
  );
}

export default Tables;
