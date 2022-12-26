import * as React from "react";
import { useEffect, useState } from "react";
import dayjs from 'dayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import { DataGrid } from '@mui/x-data-grid';
import MDSnackbar from "../../components/MDSnackbar";
// Data
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { TextField } from "@mui/material";


function Tables() {

  //entities
  const [users, setUsers] = useState([]);
  const [consultants, setConsultants] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [selectedConsultant, setSelectedConsultant] = useState();
  const [selectedUser, setSelectedUser] = useState();
  const [selectedBooking, setSelectedBooking] = useState();


  //customer
  const [update_user_name, setUpdateUserName] = useState('');
  const [update_user_preference, setUserPreference] = useState('');

  //consultant
  const [update_name, setUpdateName] = useState('');
  const [update_type, setUpdateType] = useState('');
  const [update_speciality, setUpdateSpeciality] = useState('');
  const [update_rate, setUpdateRate] = useState(0);
  const [update_company_cut, setUpdateCompanyCut] = useState(0);

  //booking
  const [value, setValue] = React.useState(dayjs(new Date().toString()));
  const [hours, setHours] = useState(0)

  //notification
  const [snackbar, setSnackbar] = React.useState({
    open: false,
    message: "",
    color: "success",
  });

  //search
  const [search, setSearch] = useState('');
  const [search_user, setSearchUser] = useState('');

  const handleChange = (newValue) => {
    setValue(newValue);
  };


  //cunsultants-service
  const FetchConsultants = async () => {

    let promise = await fetch("http://localhost:9000/resource-management-service/consultants", { method: "GET" });
    let result = await promise.json();
    console.log(result);
    setConsultants(result);
  }

  const handleDeleteConsultant = async () => {

    let promise = await fetch(`http://localhost:9000/resource-management-service/delete/${selectedConsultant.uuid}`, { method: "DELETE" });
    if (promise.ok) {
      await FetchConsultants();
    } else {
      console.log("Error occurred when deleting consultant");
    }

  }

  const handleUpdateConsultant = async () => {

    let promise = await fetch("http://localhost:9000/resource-management-service/update_consultant", {
      method: "POST", headers: {
        'Content-Type': 'application/json',

      }, body: JSON.stringify({ value: { uuid: selectedConsultant.uuid, name: update_name, type: update_type, speciality: update_speciality, rate: update_rate } })
    });
    let result = await promise.json();
    console.log(result);
    FetchConsultants();
  }

  //users-service
  const FetchUsers = async () => {
    let promise = await fetch("http://localhost:9000/customer-management-service/get-all", { method: "GET" });
    let result = await promise.json();
    console.log(result);
    console.log(JSON.stringify(result));
    setUsers(result);
  }

  const handleUpdateUser = async () => {
    let promise = await fetch("http://localhost:9000/customer-management-service/update", {
      method: "PUT", headers: {
        'Content-Type': 'application/json',
      }, body: JSON.stringify({ uuid: selectedUser.uuid, name: update_user_name, specialityPreference: update_user_preference })
    });

    FetchUsers();

  }

  const handleDeleteUser = async () => {
    console.log(selectedUser);
    fetch(`http://localhost:9000/customer-management-service/delete?uuid=${selectedUser.uuid}`, { method: "DELETE" })
      .then(() =>
        setSelectedUser(null),
        FetchUsers()
      ).catch((err) => {
        console.log(err);
      })

  }

  //booking services
  const FetchBookings = async () => {
    let promise = await fetch("http://localhost:9000/timetabling-service/get-all", { method: "GET" });
    let result = await promise.json();
    console.log(result);


    //change start and end dates in result to string
    result.forEach((booking) => {
      booking.start = booking.start.toString();

      booking.end = booking.end.toString();
    })

    setBookings(result);
  }

  const handleDeleteBooking = async () => {
    console.log(selectedBooking);
    fetch(`http://localhost:9000/timetabling-service/delete?uuid=${selectedBooking.uuid}`, { method: "DELETE" })
      .then(async (res) => {
        setSelectedBooking(null);
        const x = await res.json();

        FetchBookings();
      }).catch((err) => {
        console.log(err);
      });
  }

  const handleBookConsultant = async () => {

    const startDate = value.toISOString();
    const endDate = value.add(hours, 'hours').toISOString();

    const start = startDate.substring(0, startDate.length - 5);
    const end = endDate.substring(0, endDate.length - 5);

    const booking = {
      consultantUuid: selectedConsultant.uuid,
      customerUuid: selectedUser.uuid,
      start: start,
      end: end
    }


    let promise = await fetch("http://localhost:9000/timetabling-service/create",
      {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(booking)
      });

    if (!promise.ok) {
        setSnackbar({
            open: true,
            message: `Failed to create booking`,
            color: "error",
        });
    } else {
        let result = await promise.json();
        console.log(result);

        setSnackbar({
            open: true,
            message: `User ${result["uuid"]} Creation Successfully`,
            color: "success",
        });
    }


    FetchBookings();

  }

  //search functions
  const handleSearchConsultant = async () => {
    console.log(search);
    let promise = await fetch(`http://localhost:9000/resource-management-service/getallconsultants/${search}`, { method: "GET" });
    let result = await promise.json();
    console.log(result);
    setConsultants(result);
  }
  const handleSearchUser = async () => {
    let promise = await fetch(`http://localhost:9000/customer-management-service/get-all-by-preference?specialityPreference=${search_user}`, { method: "GET" });
    let result = await promise.json();
    console.log(result);
    setUsers(result);
  }

  //refresh state
  useEffect(() => {
    FetchConsultants();
    FetchUsers();
    FetchBookings();
  }, [selectedConsultant, selectedUser, selectedBooking]);


  //table structure
  const columns = [

    { field: 'uuid', headerName: 'ID', width: 350 },
    { field: 'name', headerName: 'Name', width: 100 },
    { field: 'type', headerName: 'Type', width: 100 },
    { field: 'speciality', headerName: 'Speciality', width: 250 },
    { field: 'rate', headerName: 'Rate', width: 70 },
    { field: 'companyCut', headerName: 'Company Cut', width: 130 },

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

      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '3%', marginBottom: '2%' }}>
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
        sx={{ minHeight: 250 }}
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
          if (selectedRowData[0] !== undefined && selectedRowData[0] !== null) {
            setUpdateName(selectedRowData[0].name);
            setUpdateType(selectedRowData[0].type);
            setUpdateSpeciality(selectedRowData[0].speciality);
            setUpdateRate(selectedRowData[0].rate);
            setUpdateCompanyCut(selectedRowData[0].companyCut);
          }

        }}


      />


      {
        selectedConsultant && !selectedUser &&
        <div style={{ display: 'flex', width: '100%', flexDirection: "row" }}>
          <h3 onClick={handleDeleteConsultant} style={{ cursor: 'pointer' }}>Delete</h3>
          <div style={{ display: 'flex', flexDirection: 'column', marginLeft: '5%' }}>
            <h3>Update</h3>
            <input type="text" placeholder={selectedConsultant.name} value={update_name} onChange={(e) => { setUpdateName(e.target.value) }} style={{ marginTop: '2%' }} />
            <input type="text" placeholder={selectedConsultant.type} value={update_type} onChange={(e) => { setUpdateType(e.target.value) }} style={{ marginTop: '2%' }} />
            <input type="text" placeholder={selectedConsultant.speciality} value={update_speciality} onChange={(e) => { setUpdateSpeciality(e.target.value) }} style={{ marginTop: '2%' }} />
            <input type="number" placeholder={selectedConsultant.rate} value={update_rate} onChange={(e) => { setUpdateRate(e.target.value) }} style={{ marginTop: '2%' }} />



            <button onClick={handleUpdateConsultant} style={{ marginTop: '2%' }}>Submit</button>

          </div>
        </div>

      }
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '3%', marginBottom: '2%' }}>
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
        sx={{ minHeight: 270 }}
        rows={user_rows}
        columns={user_columns}
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
          if (selectedRowData[0] !== undefined && selectedRowData[0] !== null) {

            setUpdateUserName(selectedRowData[0].name);
            setUserPreference(selectedRowData[0].specialityPreference);
          }

        }}


      />

      {
        selectedUser && <div style={{ display: 'flex', width: '100%', flexDirection: "row", marginBottom: '7%' }}>
          <h3 onClick={handleDeleteUser} style={{ cursor: 'pointer' }}>Delete</h3>
          <div style={{ display: 'flex', flexDirection: 'column', marginLeft: '5%' }}>
            <h3>Update</h3>
            <input type="text" placeholder={selectedUser.name} value={update_user_name} onChange={(e) => { setUpdateUserName(e.target.value) }} style={{ marginTop: '2%' }} />
            <input type="text" placeholder={selectedUser.specialityPreference} value={update_user_preference} onChange={(e) => { setUserPreference(e.target.value) }} style={{ marginTop: '2%' }} />

            <button onClick={handleUpdateUser} style={{ marginTop: '2%' }}>Submit</button>

          </div>

          <div style={{ display: 'flex', flexDirection: 'column', marginLeft: '5%' }}>
            <h3 style={{ marginBottom: '2%' }}>Book Consultant</h3>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DateTimePicker
                label="Date&Time picker"
                value={value}
                onChange={handleChange}
                renderInput={(params) => <TextField {...params} />}

              />
            </LocalizationProvider>
            <input type="number" placeholder={"hours"} value={hours} onChange={(e) => { setHours(e.target.value) }} style={{ marginTop: '4%' }} />

            <button onClick={handleBookConsultant} style={{ marginTop: '2%' }}>Submit</button>
          </div>

        </div>

      }


      <h3 style={{ marginTop: '10%' }}>BOOKING TABLE</h3>
      <DataGrid
        sx={{ minHeight: 250 }}
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
          if (selectedRowData[0] !== undefined && selectedRowData[0] !== null) {


          }

        }}
      />

      {
        selectedBooking &&
        <div style={{ display: 'flex', width: '100%', flexDirection: "row" }}>
          <h3 onClick={handleDeleteBooking} style={{ cursor: 'pointer' }}>Delete</h3>

        </div>

      }
    </DashboardLayout>
  );
}

export default Tables;
