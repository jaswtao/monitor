db.createUser(
    {
        user: "admin",
        pwd: "Passw0rd",
        roles:[
            {role: "userAdminAnyDatabase", db:"admin"}
        ]
    }
);

db.auth("admin","Passw0rd");

db.createUser(
    {
        user: "monitor",
        pwd: "123456",
        roles:[
            {role: "readWrite", db: "monitor"}
        ]
    }
);

db.auth("monitor","123456");


db.find({duration: {"$gt":3000}})