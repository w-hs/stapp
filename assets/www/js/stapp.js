var stapp =
    {
        trainingTimer: null,
        timerValue:0,
        timerInterval:100,
        updateTraining: function (jsonStr) {
            var t = JSON.parse(jsonStr);
            var training = new training(t.distance, t.heartfrequence);
            $('#distance > .DataContent').html(training.distance + "km");
            $('#heartfreq > .DataContent').html(training.heartfreq + "bpm");
        },
        startTraining: function () { 
        	if(this.trainingTimer != null)
        		window.clearInterval(this.trainingTimer);       	
            this.trainingTimer = window.setInterval(function () { stapp.updateStopWatch() }, this.timerInterval);
        },
        stopTraining: function(){        	
        	this.timerValue = 0;
        	this.trainingTimer = null
        	window.clearInterval(this.trainingTimer);
        },
        pauseTraining: function(){        	
        	window.clearInterval(this.trainingTimer);        	
        },
        updateStopWatch: function () {
            this.timerValue += this.timerInterval;
            $('#stoppwatch > .DataContent').html(this.formatTime(this.timerValue));
        },
        setHistoryData: function (jsonStr) {

            if (jsonStr != undefined) {
                var h = JSON.parse(jsonStr);
                if (h != undefined) {
                    var sessions = new Array();
                    for (var i = 0; i < h.sessions.length; i++) {
                        var session = h.sessions[i];
                        var newSession = new this.session(session.date, session.distance, session.duration, session.id);
                        sessions.push(newSession);
                    }
                    var history = new this.history(h.distanceSum, h.runCounter, h.durationSum, sessions);
                    this.displayHistoryData(history);
                }
            }
        },
        displayHistoryData: function (history) {
            
            $('#overview > #runCount > .content').html(history.runs);
            $('#overview > #distance > .content').html(history.distance);
            $('#overview > #duration > .content').html(history.duration);

            $('#sessiondetails').html("");
            for (var i = 0; i < history.sessions.length; i++) {
                var session = history.sessions[i];
                $('#sessiondetails').append(this.getSessionHTML(session));
            }
        },
        formatTime: function(miliseconds){
            var d = new Date(miliseconds);
            var h = d.getHours() - 1, m = d.getMinutes(), s = d.getSeconds();
            if(h > 0){
                if(h < 10)
                    h = "0" + h;
            }
            else
                h = "00";
            if(m > 0){
                if(m < 10)
                    m = "0" + m;
            }
            else
                m = "00";
            if(s > 0){
                if(s<10)
                    s = "0" + s;
            }
            else 
                s = "00";

            return h + ":" + m + ":" + s;
        },
        loadSessionDetails: function(id){
            Android.selectTrainingSession(id);
        },
        getSessionHTML: function (session) {
            var sessionStr =
                "<div class='session' onclick='stapp.loadSessionDetails("+session.id+");'>" +
                "<div class='date'>" +
                session.date +
                "</div>" + 
                "<div class='duration'>" +
                this.formatTime(session.duration) +
                "</div>" +
                "<div class='distance'>" +
                session.distance + 
                "km</div>" +
                "<div class='clear'></div>" +
                "</div>";
            return sessionStr;
        },
        training: function (km, bpm) {
            this.distance = km;
            this.heartfreq = bpm;
        },

        history: function (distance, runs, duration, sessions) {
            this.distance = distance;
            this.runs = runs;
            this.duration = duration;
            this.sessions = sessions;
        },

        session: function (date, distance, duration, id) {
            this.date = date;
            this.id = id;
            this.distance = distance;
            this.duration = duration;
        }
    };
