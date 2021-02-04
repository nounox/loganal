package pl.nounox.interview.loganal.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_entry")
public class LogEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the unique event identifier
	 */
	@Id
	private String id;
	
	/**
	 * duration from LogEventState.STARTED to LogEventState.FINISHED
	 */
	private long duration;

	private String type;

	private String host;
	
	private boolean alert;

	public String getId() {
		return id;
	}

	public LogEntry setId(String id) {
		this.id = id;

		return this;
	}

	public long getDuration() {
		return duration;
	}

	public LogEntry setDuration(long duration) {
		this.duration = duration;

		return this;
	}

	public String getType() {
		return type;
	}

	public LogEntry setType(String type) {
		this.type = type;

		return this;
	}

	public String getHost() {
		return host;
	}

	public LogEntry setHost(String host) {
		this.host = host;

		return this;
	}

	public boolean isAlert() {
		return alert;
	}

	public LogEntry setAlert(boolean alert) {
		this.alert = alert;

		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alert ? 1231 : 1237);
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEntry other = (LogEntry) obj;
		if (alert != other.alert)
			return false;
		if (duration != other.duration)
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "LogEntry [id=" + id + ", duration=" + duration + ", type=" + type + ", host=" + host + ", alert="
				+ alert + "]";

	}
}
