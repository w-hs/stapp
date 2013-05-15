var stapp = {
	trainingTimer : null,
	timerValue : 0,
	timerInterval : 100,
	updateTraining : function(jsonStr) {
		var t = JSON.parse(jsonStr);
		var training = new this.training(t.distance, t.heartfrequence);
		$('#distance > .DataContent').html(
				this.formatDistance(training.distance));
		$('#heartfreq > .DataContent').html(training.heartfreq + " bpm");
	},
	startTraining : function() {
		if (this.trainingTimer != null)
			window.clearInterval(this.trainingTimer);
		this.trainingTimer = window.setInterval(function() {
			stapp.updateStopWatch()
		}, this.timerInterval);
	},
	stopTraining : function() {
		this.timerValue = 0;
		window.clearInterval(this.trainingTimer);
		this.trainingTimer = null
	},
	pauseTraining : function() {
		window.clearInterval(this.trainingTimer);
	},
	updateStopWatch : function() {
		this.timerValue += this.timerInterval;
		$('#stoppwatch > .DataContent').html(
				this.formatTime(this.timerValue, false));
	},
	setHistoryData : function(jsonStr) {

		if (jsonStr != undefined) {
			var h = JSON.parse(jsonStr);
			if (h != undefined) {
				var groupedSessions = new Array();
				for ( var session in h.groupedSessions) {

					var sessions = new Array();
					for ( var i = 0; i < h.groupedSessions[session].length; i++) {
						var s = h.groupedSessions[session][i];
						var newSession = new this.session(s.date, s.distance,
								s.duration, s.id);
						sessions.push(newSession);
					}
					var groupedSession = new this.sessionGroup(session,
							sessions);
					groupedSessions.push(groupedSession);
				}

				var history = new this.history(h.distanceSum, h.runCounter,
						h.durationSum, groupedSessions);
				this.displayHistoryData(history);
			}
		}
	},
	displayHistoryData : function(history) {

		$('#overview > #runCount > .content').html(history.runs);
		$('#overview > #distance > .content').html(
				this.formatDistance(history.distance));
		$('#overview > #duration > .content').html(
				this.formatTime(history.duration, true));

		$('#sessiondetails').html("");
		for ( var i = 0; i < history.sessions.length; i++) {
			var gSession = history.sessions[i];
			var header = $('<h3></h3>').html(gSession.month);
			var container = $('<div></div>');
			for ( var j = 0; j < gSession.sessions.length; j++) {
				container.append(this.getSessionHTML(gSession.sessions[j]));
			}
			$('#sessiondetails').append(header);
			$('#sessiondetails').append(container);
		}
		$("#sessiondetails").accordion({
			collapsible : true
		});
	},
	formatTime : function(miliseconds, withSuffix) {
		var d = new Date(miliseconds);
		var h = d.getHours() - 1, m = d.getMinutes(), s = d.getSeconds();
		if (h > 0) {
			if (h < 10)
				h = "0" + h;
		} else
			h = "00";
		if (m > 0) {
			if (m < 10)
				m = "0" + m;
		} else
			m = "00";
		if (s > 0) {
			if (s < 10)
				s = "0" + s;
		} else
			s = "00";

		var suffix = "h";
		var ret = "";
		if (h != "00") {
			ret += h;
			ret += ":";
			suffix = " h";
		} else
			suffix = " m";
		ret += m;
		ret += ":";
		ret += s;
		if (withSuffix)
			ret += suffix;

		return ret;
	},
	formatDistance : function(distance) {
		// Distanz kommt in Meter
		if (distance > 1000) {
			var nDistance = distance / 1000;
			return nDistance.toFixed(2) + " km";
		}
		return distance + " m";
	},
	loadSessionDetails : function(id) {
		Android.selectTrainingSession(id);
	},
	getSessionHTML : function(session) {
		var sessionStr = "<div class='session' onclick='stapp.loadSessionDetails("
				+ session.id
				+ ");'>"
				+ "<div class='date'>"
				+ session.date
				+ "</div>"
				+ "<div class='duration'>"
				+ this.formatTime(session.duration)
				+ "</div>"
				+ "<div class='distance'>"
				+ this.formatDistance(session.distance)
				+ "</div>"
				+ "<div class='clear'></div>" + "</div>";
		return sessionStr;
	},
	training : function(km, bpm) {
		this.distance = km;
		this.heartfreq = bpm;
	},

	history : function(distance, runs, duration, sessions) {
		this.distance = distance;
		this.runs = runs;
		this.duration = duration;
		this.sessions = sessions;
	},

	sessionGroup : function(month, sessions) {
		this.month = month;
		this.sessions = sessions;
	},

	session : function(date, distance, duration, id) {
		this.date = date;
		this.id = id;
		this.distance = distance;
		this.duration = duration;
	}
};
