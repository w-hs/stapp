var stapp =
    {
        trainingTimer: null,
        timerValue:0,
        timerInterval:100,
        updateTraining: function (jsonStr) {
            console.log(jsonStr);
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
            var date = new Date(this.timerValue);
            var hours = date.getHours() - 1 < 10 ? "0" + date.getHours() - 1 : date.getHours() - 1;
            var mins = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var secs = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            var timeStr = hours + ":" + mins + ":" + secs;
            $('#stoppwatch > .DataContent').html(timeStr);
        }
    };

function training(km, bpm) {
    this.distance = km;
    this.heartfreq = bpm;
}