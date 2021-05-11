# Endpoints that Frontend is probably going to use


Remote API root: https://bg-emotion-tracker-be-b.herokuapp.com/
Local API root: http://localhost:2019/

## Endpoints for Users
### User getting their own userinfo

GET /users/getuserinfo

with response
```
{
    "userid": 5,
    "username": "llama001@maildrop.cc",
    "useremails": [],
    "roles": [
        {
            "role": {
                "roleid": 1,
                "name": "ADMIN"
            }
        }
    ]
}
```


## Endpoints for Clubs
### Getting list of club names and ids
GET /clubs/summary *** not implemented yet ***

reponses ommited

### Getting a club by ID
This is current way to get list of activities by club

GET /clubs/club/{clubid}

sample response
```
{
    "clubid": 23,
    "activities": [
        {
            "activity": {
                "activityid": 13,
                "activityname": "Club Attendance"
            }
        },
        {
            "activity": {
                "activityid": 14,
                "activityname": "Arts & Crafts"
            }
        },
        {
            "activity": {
                "activityid": 16,
                "activityname": "Basketball"
            }
        }
    ],
    "users": [],
    "clubname": "johnston"
}
```



##  Endpoints for Activities
### Posting new activity to club

POST /activities/activity/addclub/{clubid}

with body
```
{
    "activityname": "test"
}
```
If the activity name is not found in DB, a new activity is created.
Or if the frontend knows the activity ID, you can use this too:
```
{
    "activityid": 15;
}
```




