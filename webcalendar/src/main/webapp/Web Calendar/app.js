const months = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"];

const daysOfWeek = ["Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"];


$(document).ready(function () {

    let date = new Date();
    let currMonth = date.getMonth();
    let currYear = date.getFullYear();
	
    for (let i = 0; i < months.length; i++) {
        $("#month").append(`<option name="${i}">${months[i]}</option>`)
    }

    for (let i = 0; i < 11; i++) {
        $("#year").append(`<option name="${currYear + i}">${currYear + i}</option>`)
    }

    $(`option[name=${currMonth}]`).attr("selected", "selected"); // Choose current month as default month.
    $(`option[name=${currYear}]`).attr("selected", "selected"); // Choose current year as default year.

    generateCal(currMonth, currYear); // Display the current month's days.

    $("#month").on('change', function () {
        let optName = $(this).find('option:selected').attr('name');
        console.log(optName);
        currMonth = Number(optName);
        generateCal(currMonth, currYear);
    });

    $("#year").on('change', function () {
        let optName = $(this).find('option:selected').attr('name');
        console.log(optName);
        currYear = Number(optName);
        generateCal(currMonth, currYear);
    });

    $(document).on('click', '.tas', (function () { // If user clicks on the textarea a prompt will be generated to get the reminder input and put it into the textarea.
        //console.log("In!")
        //console.log($(this).find("div").eq(0).html()); 
        let text = "Enter Reminder for: " + $(this).parent().find("div").eq(0).html() + ", " + months[currMonth] + " " + $(this).parent().find("div").eq(1).html() + ", " + currYear;
        let defaultText = "Enter Reminder";
        let reminder = window.prompt(text, defaultText);
        if (reminder != null && reminder !== "") {
            let temp = "-" + reminder + ".\n";
			let secondDiv = $(this).parent().children().eq(1);
            let date = secondDiv.text() + "-" + padZero(currMonth + 1) + "-" + currYear;
			let myData = { "reminder": temp, "date": date };
            $.ajax({
                url: '/webcalendar/Calendar',
                type: 'POST',
                data: JSON.stringify(myData),
    			contentType: "application/json",
                dataType: "text",
                success: function (response) {
                    console.log(response);
                },
                error: function (jqXHR, exception) {
                    var msg = '';
                    if (jqXHR.status === 0) {
                        msg = 'Not connected.\nVerify Network.';
                    } else if (jqXHR.status == 404) {
                        msg = 'Status 404: Requested page not found.';
                    } else if (jqXHR.status == 500) {
                        msg = 'Status 500: Internal Server Error.';
                    } else if (exception === 'parsererror') {
                        msg = 'Requested JSON parse failed.';
                    } else if (exception === 'timeout') {
                        msg = 'Time out error.';
                    } else if (exception === 'abort') {
                        msg = 'Ajax request aborted.';
                    } else {
                        msg = 'Uncaught Error.\n' + jqXHR.responseText;
                    }
                    console.log(msg);
                }
            });
            $(this).append(temp);
        }
    }));

    $(document).on('click', '.showbutton', function () {
        let content = $(this).parent().parent().find("textarea").val();
        if (content != null && content !== "") {
            let text = $(this).parent().parent().find("div").eq(0).html() + ", " + months[currMonth] + " " + $(this).parent().parent().find("div").eq(1).html() + ", " + currYear + "\n";
            alert(text + content);
        }
    })

     $(document).on('click', '#logout', function () {
        $.ajax({
            url: '/webcalendar/Logout',
            type: 'POST',
            dataType: "json",
            success: function (response) {
				//console.log(response);
                window.location.href = response;
            },
            error: function (jqXHR, exception) {
                var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connected.\nVerify Network.';
                } else if (jqXHR.status == 404) {
                    msg = 'Status 404: Requested page not found.';
                } else if (jqXHR.status == 500) {
                    msg = 'Status 500: Internal Server Error.';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
                console.log(msg);
            }
        });
    });
	
});

function generateCal(month, year) {
    $("#div1").empty();
    let temp = getDaysOfMonth(month, year);
    for (let i = 0; i < temp.length; i++) {
        //    console.log(temp[i]);
		let day = padZero(i + 1);
        $("#div1").append(`<button id="butt${day}"></button>`);
        $(`#butt${day}`).append(`<div>${daysOfWeek[temp[i].getDay()].toUpperCase()}</div>`);
        $(`#butt${day}`).append(`<div>${day}</div>`);
        $(`#butt${day}`).append("<break></break>");
        $(`#butt${day}`).append("<textarea class='tas' placeholder='CLICK TO ADD REMINDER'></textarea>");
        $(`#butt${day}`).append(`<div id="div3"><button class="showbutton">Show All Reminders</button></div>`);
        //    console.log((i + 1) + " : " + daysOfWeek[temp[i].getDay()]);
    }
	generateReminders(padZero(month + 1), year);
}

function padZero(num) {
    let x = parseInt(num);
    if (x % 10 == x) return "0" + num;
	else return num;
}

// Month is 0-indexed.
function getDaysOfMonth(month, year) {
    let d = new Date(year, month, 1);
    let days = [];
    while (d.getMonth() === month) {
        days.push(new Date(d));
        d.setDate(d.getDate() + 1); // The setDate() method changes the day of the month of a given Date instance, based on local time.
    }
    return days;
}

// Get all the reminders for this user.
async function getReminders() {

	//console.log("Inside getReminders");
    return $.ajax({
        dataType: 'json',
        url: '/webcalendar/Calendar',
        type: 'GET',
		cache: false
    })
	.then(function (response) {
            //console.log(response);
			return response;
        });
}

async function generateReminders(month, year) {
    let response = await getReminders();
	generateWelcomeMsg(response.username);
    let reminders = response.reminders;
    for (let date in reminders) {
        //console.log("Date: " + date + ", Reminder: " + reminders[date]);
        let dateSplit = date.split("-");
		//console.log(dateSplit[0] + " " + dateSplit[1]);
		//console.log("Compare year: " + dateSplit[2].localeCompare(year));
		//console.log("Compare month: " + dateSplit[1].localeCompare(month));
        if (dateSplit[2].localeCompare(year) == 0 && dateSplit[1].localeCompare(month) == 0) {
            generateRemindersHelper(dateSplit[0], reminders[date]);
        }
    }
}

function generateWelcomeMsg(name) {
	$("#sp").text("Hi, " + name + "!");
}

function generateRemindersHelper(day, reminder) {
	//console.log("Day: " + day + ", Reminder: " + reminder);
    let dayDiv = $(`#butt${day}`).children().eq(3);
	let taText = dayDiv.val();
	dayDiv.val(taText + reminder);
}