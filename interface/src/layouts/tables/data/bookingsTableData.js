/* eslint-disable react/prop-types */
/* eslint-disable react/function-component-definition */
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

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDAvatar from "components/MDAvatar";
import MDBadge from "components/MDBadge";
import * as React from "react";
import { useState } from "react";
// Images
import team2 from "assets/images/team-2.jpg";
import team3 from "assets/images/team-3.jpg";
import team4 from "assets/images/team-4.jpg";

export default function Data() {
  const [Bookings, setBookings] = useState([]);

//fetch all consultants
  const bookings=async()=>{
   await  fetch("http://localhost:9000/resource-managements-service/getallbookings").then(async(response) => {
     await response.json().then((data) => {
        setBookings(data);
        console.log(data);
      });
    })

  }

  const Edit=()=>{
    //just loop through consultants
    //if id matches then edit

      //i is not defined

       for (let i in Bookings){
          Bookings[i].edit =
            <MDTypography component="a" href="#" variant="caption" color="text" fontWeight="medium">
              Edit
            </MDTypography>
            
          }
        


      

  }
  const Delete=()=>{
    //just loop through consultants
    //if id matches then edit

      //i is not defined

       for (let i in Bookings){
          Bookings[i].delete =
            <MDTypography component="a" href="#" variant="caption" color="text" fontWeight="medium">
              Delete
            </MDTypography>
            
          }
        


      

  }

  const addEDitConsultant=async()=>{
    await fetch("http://localhost:9000/resource-managements-service/addbooking", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: "test",
        id: "test",
        type: "test",
        speciality: "test",
        rate: "test",
      }),
    }).then(async(response) => {
      await response.json().then((data) => {
        console.log(data);
      });
    })
  }


  // React.useEffect(() => {
  //   consultants();
  //   Edit();
  // }, []);
 
  return {
    columns: [
      { Header: "ConsultantId", accessor: "ConsultantId", width: "45%", align: "left" },
      { Header: "CustumerId", accessor: "CustomerId", align: "left" },
      { Header: "Date", accessor: "Date", align: "center" },
      { Header: "Time", accessor: "Time", align: "center" },
      { Header: "Hours", accessor: "Hours", align: "center" },
      { Header: "edit", accessor: "edit", align: "center" },
      { Header: "delete", accessor: "delete", align: "center" },
    ],

    rows : Bookings,

     
       
       
  };
}


