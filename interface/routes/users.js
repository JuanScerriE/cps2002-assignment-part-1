var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/echo', async function(req, res, next) {
  let response = await fetch("http://gateway:9000/timetabling-service/getecho");
  res.send(await response.text());
});

module.exports = router;
