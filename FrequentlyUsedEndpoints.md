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

- No request body needed.

Explanation with a example url:
- POST /memberreactions/memberreaction/submit?mid=testmember1&aid=13&cid=20&rx=1F601
    - memberid: testmember1
    - activityid: 13
    - clubid: 20
    - Emojivalue: 1F601 (means U+1F601)





<br />


### Searching for MemberReactions (Beta)
- Can be used as a way to query general feedbacks to a specific club activity.
- Can be used as a way to query how a particular member is doing.
- Could be used in fancy ways as comparisons against multiple club activities and/or members

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

- Another endpoint that derives from the one above shall be made that would respond with formatted arrays specifically for plotting charts/analysis on the frontend.
    - restrict to club-activities to up to 1
        - A plot that compares how different members are feeling towards one specific club-activity 
    - restrict members to up to 1
        - A plot that compares how a single member feel towards different club-activities




