package de.whs.stapp.presentation.viewmodels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import de.whs.stapp.data.storage.TrainingSession;

/***
 * Enthält alle Daten, die auf der HistoryView angezeigt werden.
 * 
 * @author Thomas
 * 
 */
public class History extends StappViewModel {

	private int distanceSum = 0;
	private int runCounter = 0;
	private long durationSum = 0;
	private LinkedHashMap<String, List<Session>> groupedSessions = new LinkedHashMap<String, List<Session>>();

	private final int currentYear = new Date().getYear();

	/**
	 * Erstellt eine neue Instanz der History-Klasse. Dabei muss die Liste der
	 * Sessions übergeben werden um die Membervariablen zu initialisieren.
	 * 
	 * @param sessions
	 *            Enthält die Liste der Trainingssession, die zur Aggregration
	 *            der Überblicksdaten genutzt werden.
	 */
	public History(ArrayList<TrainingSession> sessions) {

		Collections.sort(sessions);

		for (TrainingSession session : sessions) {

			distanceSum += session.getDistanceInMeters();
			runCounter++;
			durationSum += session.getDurationInMs();
			aggregateSession(session);
		}

		for (List<Session> sessionsFromMonth : groupedSessions.values()) {
			Collections.sort(sessionsFromMonth);
		}
	}

	private void aggregateSession(TrainingSession session) {

		String month = "";

		if (session.getTrainingDate().getYear() == currentYear) {
			month = new SimpleDateFormat("MMM", Locale.GERMANY).format(session
					.getTrainingDate());
		} else {

			month = new SimpleDateFormat("MMM yyyy", Locale.GERMANY)
					.format(session.getTrainingDate());
		}

		if (groupedSessions.containsKey(month)) {
			((List<Session>) groupedSessions.get(month))
					.add(convertTrainingSessionToViewModel(session));
		} else {
			List<Session> sessions = new ArrayList<Session>();
			sessions.add(convertTrainingSessionToViewModel(session));
			groupedSessions.put(month, sessions);
		}
	}

	private Session convertTrainingSessionToViewModel(TrainingSession session) {

		return new Session(session.getTrainingDate(),
				(int) session.getDistanceInMeters(), session.getDurationInMs(),
				session.getSessionId());
	}

	/**
	 * @return Gibt die Distanz zurück.
	 */
	public int getDistanceSum() {
		return distanceSum;
	}

	/**
	 * @return Gibt die Anzahl der Läufe zurück.
	 */
	public int getRunCounter() {
		return runCounter;
	}

	/**
	 * @return Gibt die gesamte Dauer zurück.
	 */
	public long getDurationSum() {
		return durationSum;
	}

	/**
	 * @return Gibt die Sessions zurück.
	 */
	public LinkedHashMap<String, List<Session>> getSessions() {
		return groupedSessions;
	}
}
