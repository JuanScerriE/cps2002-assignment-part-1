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
  const [Consultants, setConsultants] = useState([]);

//fetch all consultants
  const consultants=async()=>{
   await  fetch("http://localhost:9000/resource-management-service/consultants").then(async(response) => {
     await response.json().then((data) => {
        setConsultants(data);
        console.log(data);
      });
    })

  }
  const handleDelete = async (id) => {
    await fetch("http://localhost:9000/resource-managements-service/deleteconsultant/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(async(response) => {
      await response.json().then((data) => {
        console.log(data);
      });
    })
  }

  const handleEdit = async (id) => {
    await fetch("http://localhost:9000/resource-managements-service/updateconsultant/" + id, {
      method: "PUT",
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


  const Edit=()=>{
    //just loop through consultants
    //if id matches then edit

      //i is not defined

       for (let i in Consultants){
          Consultants[i].action =
            <MDTypography component="a" onClick={()=>handleDelete(Consultants[i].id)} variant="caption" color="text" fontWeight="medium">
              Edit
            </MDTypography>
            
          }
        


      

  }

  const Delete=()=>{
    //just loop through consultants
    //if id matches then edit

      //i is not defined

       for (let i in Consultants){
          Consultants[i].delete =
            <MDTypography component="a" onClick={()=> handleEdit(Consultants[i].id)} variant="caption" color="text" fontWeight="medium">
              Delete
            </MDTypography>
            
          }
  }

  const addEDitConsultant=async()=>{
    await fetch("http://localhost:9000/resource-managements-service/addconsultant", {
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
      { Header: "name", accessor: "name", width: "45%", align: "left" },
      { Header: "id", accessor: "id", align: "left" },
      { Header: "type", accessor: "type", align: "center" },
      { Header: "speciality", accessor: "speciality", align: "center" },
      { Header: "rate", accessor: "rate", align: "center" },
      { Header: "edit", accessor: "edit ", align: "center" },
      { Header: "delete", accessor: "delete", align: "center" },
    ],

    rows : Consultants,

     
       
       
  };
}


