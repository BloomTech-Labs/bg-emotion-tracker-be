# Test Data
## Login Accounts
- llama001@maildrop.cc -> ADMIN
- llama002@maildrop.cc -> YDP
- llama003@maildrop.cc -> CD

## Existing Reactions Strings Values
- 1F601 (😁)
- 1F642 (🙂)
- 1F610 (😐)
- 1F641 (🙁)
- 1F61E (😞)

## Existing Members ID Value
- testmember1
- testmember2
- testmember3
- testmember4

## Testing clubname-memberid csv
- https://cloud.obtl.me/index.php/s/rgEcS64bmHbTQ3B


# Major Endpoints for frontend features 

Remote API root: https://bg-emotion-tracker-be-b.herokuapp.com/
Local API root: http://localhost:2019/

## Endpoints for Users
### User getting their own userinfo

GET /users/getuserinfo

sample response
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
GET /clubs/summary

sample response
```
[
    {
        "clubid": 20,
        "clubname": "anderson"
    },
    {
        "clubid": 21,
        "clubname": "caitlin"
    },
    {
        "clubid": 22,
        "clubname": "grossman"
    },
    {
        "clubid": 23,
        "clubname": "johnston"
    }
]
```

<br />

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
<br />

### Adding a User to a Club
- Use this to assign a user(staff) to a club

POST /clubs/club/{cid}/addUser/{uid}

with empty body

Example url
```
https://bg-emotion-tracker-be-b.herokuapp.com/clubs/club/22/addUser/8
```
will add user with id 8 to club with id 22


<br />

### Removing a User from a club 

DELETE /clubs/club/{cid}/removeUser/{uid}

Example url
```
https://bg-emotion-tracker-be-b.herokuapp.com/clubs/club/22/removeUser/8
```

<br />


### Removing a Member from a club 
- Member would be added to the corresponding Club when posting new memberReaction, if the member is in the club yet.
- So only the delete endpoint is documented here.

DELETE /clubs/club/{cid}/removeMember/{mid}

Example url
```
https://bg-emotion-tracker-be-b.herokuapp.com/clubs/club/21/removeMember/testmember1
```

<br />

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




## Endpoints for MembersReactions

### Submitting a MemberReaction

POST /memberreactions/memberreaction/submit?mid={memberidvalue}&aid={activityid}&cid={clubid}&rx={reactionstring}

- Use empty body

Explanation with a example url:
- POST /memberreactions/memberreaction/submit?mid=testmember1&aid=13&cid=20&rx=1F601
    - memberid: testmember1
    - activityid: 13
    - clubid: 20
    - Emojivalue: 1F601 (means U+1F601)





<br />


### Searching for MemberReactions
- This endpoint returns a list of all memberReactions given filters for
   - members
   - clubActivities
   - time range

POST /memberreactions/search?from={starttime}&to={endtime}

sample uri: /memberreactions/search?from=2020-04-01&to=2021-06-01

with sample body: 
```
{
    "activities": [
        {
            "clubid": 20,
            "activityid": 13,
        },
        {
            "clubid": 20,
            "activityid": 14,
        }
    ],
    "members": [
        {
            "memberid": "testmember1"
        }, 
        {
            "memberid": "testmember2"
        }
    ]
}
```
The above query shall return all reactions from testmember1 and testmember2 for their submissions for club 20's activity 13 and 14.


## CSV

### Endpoint for CSV upload

- This endpoint handles the import of a csv list of clubname, memberid.
- The csv file can include a first line describing fields like the one in test.csv
- or not having the firstline, which default to clubname, memberid.

- Note: the current csv feature in the frontend is a simple read to list instead of handling the csv file itself
    - If that would be used, regular endpoints handling jsons shall be used.
- This endpoint requires a body of form data with the csv file.

POST /csv/upload

with form-data body of
```
{
    file: {csvfile}
}
```
where {csvfile} is a file stream of the csv file.

- This feature can be tested in the Postman collection with the given test csv above.
- Frontend can implement a feature to handle file if this endpoint is going to be used. 

<br />

### Endpoint for CSV download
GET at /csv/download

- This is not a file served by the server, so if frontend want a pop up for your typical download on the internet, maybe a new library is needed.



<br />

## Report

- Simple calculations by averaging emoji values over time frame
- We have two modes of report requesting

### Get averages of feedbacks given to activity
GET /report/club/{cid}/activities?from={fromdate}&to={todate}

- from-date and to-date are optional.
- if cid is 0, then we return all club activities
- otherwise we only get activities from club {cid}
- Sample response:
```
[
    {
        "clubname": "anderson",
        "activityname": "Club Attendance",
        "posivitiy": -0.14285714285714285
    },
    {
        "clubname": "anderson",
        "activityname": "Club Checkout",
        "posivitiy": -0.1111111111111111
    },
    {
        "clubname": "anderson",
        "activityname": "Basketball",
        "posivitiy": -0.07692307692307693
    },
    {
        "clubname": "anderson",
        "activityname": "Homework Help",
        "posivitiy": 0.5
    },
    {
        "clubname": "anderson",
        "activityname": "Archery",
        "posivitiy": -0.5454545454545454
    },
    {
        "clubname": "anderson",
        "activityname": "Arts & Crafts",
        "posivitiy": 0.2857142857142857
    }
]
```



### Get averages of feedbacks member given to activities
GET /report/club/{cid}/members?from={fromdate}&to={todate}

- Behaves the same way as above
- Sample Response:
```
[
    {
        "memberid": "testmember4",
        "posivitiy": 0.125
    },
    {
        "memberid": "testmember3",
        "posivitiy": -0.058823529411764705
    },
    {
        "memberid": "testmember2",
        "posivitiy": -0.5
    },
    {
        "memberid": "testmember1",
        "posivitiy": 0.21052631578947367
    }
]
```
