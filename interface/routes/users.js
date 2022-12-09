var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/echo', async function(req, res, next) {
  let result = await fetch("http://gateway:9000/timetabling-service/getecho",
      {
        method: 'GET',
        mode: 'cors'
      });
  console.log(result);
  console.log(result.status);
  console.log(result.body);
  console.log(JSON.stringify(result.body));
  res.send(result.text());
});

module.exports = router;
