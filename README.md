# Part-timer

<table>
    <thead>
        <tr>
            <th colspan="3">Contributers</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><b>Name</b></td>
            <td><b>Banner ID</b></td>
   	<td><b>Email ID</b></td>
        </tr>
  <tr>  
        <td>Abraham Vineel Katikala</td>
        <td> B00793815</td>
        <td>ab760856@dal.ca</td>
    </tr>
        <tr>  
        <td>Albertus Brink</td>
        <td>B00584349</td>
        <td>al663745@dal.ca</td>
    </tr>
      <tr>  
        <td>Dinesh Kuncham</td>
        <td>B00801162</td>
        <td>dinesh.kuncham@dal.ca</td>
    </tr>
      <tr>  
        <td>Mohan Pathania</td>
        <td>B00813435</td>
        <td>mohanp@dal.ca</td>
    </tr>
      <tr>  
        <td>Vamsi Krishna Reddy Dwarsala</td>
        <td>B00800531</td>
        <td>vm420113@dal.ca</td>
    </tr>
    </tbody>
</table>


  ## GitHub Repo Link - [https://github.com/albrink92/part-timer](https://github.com/albrink92/part-timer)


# Project Summary

We have developed an application that allows international students who work part-time to track their work hours reliably. The application’s main purpose is to log user’s entry and exit times at a specific geological location and to track how much time the user spent at that location. For international students, there is a limit on the number of work-hours per week. So our app makes sure that users are aware of how many hours they have worked for so that they do not cross that time limit. The app assists users in planning their time according to their university and work schedules. It also calculates the user's expected pay based on the number of hours they spend at work, which will be useful for managing their personal budget. The user can also manually add/edit the logged work-hours. 


## Libraries

**Google Maps geofencing API:** Geofencing [1] defines perimeters and triggers notifications when a user crosses that perimeter. It is used to track user’s entry/exit at a set location.
Source [here](https://developers.google.com/location-context/geofencing/)

**Room Persistence Library:** 
The Room persistence library [2] provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. The library helps you create a cache of your app's data on a device that's running your app. This cache, which serves as your app's single source of truth, allows users to view a consistent copy of key information within your app, regardless of whether users have an internet connection. 
Source [here](https://developer.android.com/topic/libraries/architecture/room)

**Architecture components**
Architecture components [3] were used to handle data persistence while showing the list of log times. 
Source [here](https://developer.android.com/topic/libraries/architecture/)

## Installation Notes
This app has to be installed on the phone as it will not work in the emulator. The reason being that the Geofencing API used in the app does not support emulators. On the phone, it is installed like any other Android package.

## Code Examples

**Problem 1: Running database queries on main thread caused a little delay in app**

We used AsyncTask [4] to leverage the multi-threading options of Android and run the database queries on a secondary thread to avoid blocking the UI thread [5]. 


```
// The method we implemented that solved our problem
AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        logData.setTracking(false);
                        logData.setPlaceID(selectedLocation);
                        appDatabase.logDataDaoModel().insert(logData);
                    }
                })
```
**Problem 2: Tracking user location using inbuilt GPS drained battery**

Using android LocationServices resulted in low power efficiency and poor GPS accuracy so we had to find another solution. The answer was  Geofencing - a feature also offered in Android Studio which more accurately tracker the user location, preserved more battery power, and worked even when the app was not running. 

```
// The method we implemented that solved our problem
@NonNull
    private GeofencingRequest geofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(mContext, GeofenceTransitionsIntentServices.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        mGeofencePendingIntent = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }
  ```


## Feature Section


### Seamless work hour tracking
Part-Timer seamlessly tracks users part-time work hours using their device’s built-in GPS system, wifi connection, and accelerometers to enable a function called Geofencing. Geofencing allows a users device to log their potential work hours even when Part-Timer is not running, ensuring that they never forget to track their hours. 


### Overture Prevention
Users will be kept up to date about their hours worked during the current week at all times with information on the home and stats page of Part-Timer to ensure that they do not exceed their strict 20 work-hour a week limit.


### Pay calculation
Users will be able to see roughly how much they have earned during the current week based on their hours worked and hourly rate. 


### Visualized Data
An overview of hours worked in the past weeks and months will be displayed in the statistics page of Part-Timer to allow users to better understand their work patterns so that they can plan for the future and understand the past.


### Control over data
As data will be stored locally on user’s devices, they will have complete control over it. Users will be able to edit and delete existing entries as well create entirely new entries manually if they wish. This allows users to rest easy knowing that their location and work-hour information is completely within their hands. 

## Low fidelity prototype

<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/lofiproto.jpg" width = 650 height = 800>

## Wireframe
Wireframes were designed using proto.io [6]. 
<div>
    <h3>Home page wireframe</h3>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/home_wireframe1.JPG" width = 300 height = 500>
</div>

<div>
    <h3>Stats page wireframe</h3>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/stats_wireframe.png" width = 300 height = 500>
</div>

<div>
    <h3>Settings page wireframe</h3>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/settings_wireframe.JPG" width = 300 height = 500>
</div>

## Site map

<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/sitemap.JPG" width = 900 height = 500>

## ER Diagram

<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/ER.jpeg" width = 900 height = 500>

## Screenshots

<div>
<h3> Home page </h3>
<p>The home page shows the check-in, check-out times and total number of hours worked in the current week. A Floating button at the bottom right of the page gives an option to the user to and and edit the check-in and check-out times. Estimated pay is also shown to the user.</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/home_page.jpeg" width=300 height=500>
</div>

<div>
<h3>Stats page</h3>
<p>The stats page has a circular progress bar which shows the weekly hours worked in that current week. Green color shows the percentage of hours worked and the remaining percentage is shown in red color. The rest of the statistics for weekly and monthly time periods are shown in bar chart.</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/stats1.jpeg" width = 300 height = 500>
</div>

<div>
    <h3> Settings page </h3>
<p>The settings page allows the user to add work location. The user can also switch between a 24 hour and 12 hour time format. The user can enter hourly pay rate of the current part time work which will be displayed as estimated pay on the home page. The user can contact us by clicking on the mail icon.</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/settings_page.jpeg" width = 300 height = 500>
</div>

<div>
    <h3> Help page</h3>
    <p>Help page shows description about each page where the user can find about what each function is doing.</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/help_page.jpeg" width = 300 height = 500>
</div>


<div>
    <h3> Edit and add log time</h3>
    <p>Floating button showing options to the user to add or edit the log times.</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/edit_add_logtime.jpeg" width = 300 height = 500>
</div>

<div>
    <h3> Add log times</h3>
    <p>If the phone runs out of power and the log time does not get recorded, we have provided a option to the user to add the work hours manually</p> 
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/add_logtimes.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Log work notification</h3>
    <p>When the user enters their work location a notification is shown to the user with the options; yes or no. We have provided a prompt to the user through which they specify whether they are there at this location to work or simply visit. If the user clicks on 'no' the time does not get logged.</p> 
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/log_work_notification.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Left work notification</h3>
    <p>The user is alerted with a notification that they have left the work location.</p>  
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/left_work_notification.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Log list</h3>
   <p> List of all work hours is shown so the user can go through them and manually edit or delete and of them if they are incorrectly logged </p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/log_list.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Checkin greater than checkout</h3>
   <p> A pop up message is shown when the user enters a checkin time greater than the checkout time (error handling)</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/msg_wrong_log_times.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Checkout less than checkin</h3>
    <p>A message is shown when the user enters a checkout time lesser than the checkin time (error handling)</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/checkout_less_than_checkin.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Checkin should not be equal to checkout</h3>
    <p>A message is shown when the user enters a checkin time equal to checkout time (error handling)</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/checkin_checkout_not_equal.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Payment notification</h3>
    <p>A popup message is shown saying that taxes are not included in the pay calculation</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/payment_notification.jpeg" width = 300 height = 500>
</div>

<div>
    <h3>Data already exists</h3>
    <p>A message is shown when the user tries to enter a checkin and checkout time combination that is already present.</p>
<img src = "https://github.com/albrink92/part-timer/blob/master/design_resources/data_already_exists_msg.jpeg" width = 300 height = 500>
</div>

## Testing methodologies

### Functionality Testing
Simulated GPS changes successfully triggered work-hour logging. Added checkin and checkout times, which successfully result in generating work-hour stats in the Stats Page. Functionally checkin time has to be lesser than checkout time, we have tested the functionality and handled the errors accordingly. The functionalities pay per hour and 24 hour time format have been tested.

### Performance Testing
Moved from Android location manager class to Google’s Geofence API to increase battery efficiency and GPS accuracy. A performance drop occurred when querying the Room database, so we implemented asynchronous database querying. 

## Final Project Status

As it stands, Part-Timer is functional though the UI needs further refinement. We achieved the majority of our functionality goals - our application successfully tracks the users location and logs time spent at designated work locations. Statistics about these tracked hours are displayed within the application, and the primary display alerting users to their remaining work hours this week has also been implemented. 

If we were to continue work, the first step would be to settle on a unifying design, as it would be the biggest hindrance to our success if we were to publish Part-Timer now. A sleek feel and unifying aesthetic are critical to success in today’s app market. 

Further development would see us begin to integrate calendars, add suggested bus routes, support multiple workplaces, and generate additional statistics. 


#### Minimum Functionality
- Log check-in time at a location (Completed)
- Log check-out time of a location (Completed)
- Calculate hours ‘worked’ at a location (Completed)

#### Expected Functionality
- Track hours worked (Completed)
- Users informed/notified about tracked work-hours (Completed)
- Show number of hours worked this day/week/month (Completed)
- Add, edit, and delete data (Completed)
- Show estimated pay for this week (Completed)

#### Bonus Functionality
- Edit check-in time and check-out time (Completed)
- Option to change time format (Completed) 
- Show suggested bus routes (Not Implemented)
- Show additional tips (Not Implemented)
- Calculate and show additional statistics (Not Implemented)
- Update estimated time of arrival on the fly (Not Implemented)
- Alert user about upcoming check-in time (Not Implemented)
- Adding additional work locations (Not Implemented)


## Sources

[1]"Create and monitor geofences  |  Android Developers", Android Developers, 2018. [Online]. Available: https://developer.android.com/training/location/geofencing. [Accessed: 05- Dec- 2018].

[2]"Room Persistence Library  |  Android Developers", Android Developers, 2018. [Online]. Available: https://developer.android.com/topic/libraries/architecture/room. [Accessed: 05- Dec- 2018].

[3]"Android Architecture Components  |  Android Developers", Android Developers, 2018. [Online]. Available: https://developer.android.com/topic/libraries/architecture/. [Accessed: 06- Dec- 2018].

[4]"AsyncTask  |  Android Developers", Android Developers, 2018. [Online]. Available: https://developer.android.com/reference/android/os/AsyncTask. [Accessed: 05- Dec- 2018].

[5]"Processes and threads overview  |  Android Developers", Android Developers, 2018. [Online]. Available: https://developer.android.com/guide/components/processes-and-threads. [Accessed: 06- Dec- 2018].

[6]"Proto.io - Prototypes that feel real", Proto.io. [Online]. Available: https://proto.io/. [Accessed: 05- Dec- 2018].

[7]"PhilJay/MPAndroidChart", GitHub, 2018. [Online]. Available: https://github.com/PhilJay/MPAndroidChart. [Accessed: 05- Dec- 2018].

[8]"Android Room with a View - Java", Codelabs.developers.google.com, 2018. [Online]. Available: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0. [Accessed: 05- Dec- 2018].

[9]"Developer Guides  |  Android Developers", Android Developers, 2018. [Online]. Available: https://developer.android.com/guide/. [Accessed: 05- Dec- 2018].

[10]"Android Asset Studio", Romannurik.github.io, 2018. [Online]. Available: https://romannurik.github.io/AndroidAssetStudio/index.html. [Accessed: 06- Dec- 2018].

