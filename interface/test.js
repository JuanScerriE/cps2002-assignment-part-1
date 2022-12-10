fetch("http://timetabling-service:9002/getecho",
    {
        method: "GET",
        mode: "cors",

    })
    .then(res => {
        console.log(res);

        return res.text();
    })
    .then(res => {
        console.log(res);
    })
    .catch(err => {
        console.log(err);
    }) ;