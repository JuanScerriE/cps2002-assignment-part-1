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

// Images
import team2 from "assets/images/team-2.jpg";
import team3 from "assets/images/team-3.jpg";
import team4 from "assets/images/team-4.jpg";

export default function data() {


  return {
    columns: [
      { Header: "name", accessor: "name", width: "45%", align: "left" },
      { Header: "id", accessor: "id", align: "left" },
      { Header: "preference", accessor: "preference", align: "center" },
      { Header: "edit", accessor: "edit", align: "center" },
      { Header: "delete", accessor: "delete", align: "center" },
      
    ],

    rows: [
     
       
          // <MDTypography component="a" href="#" variant="caption" color="text" fontWeight="medium">
          //   Edit
          // </MDTypography>
       
    ],
  };
}
