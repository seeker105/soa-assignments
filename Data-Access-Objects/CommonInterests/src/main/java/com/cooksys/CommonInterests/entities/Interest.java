package com.cooksys.CommonInterests.entities;

public class Interest {
	private Long id;
	private String title;
	
	public Interest(){
		// no-arg
	}
	
	public Interest(String title) {
		super();
		this.title = title;
		this.id = null;
	}

	public Interest(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Interest)) {
			return false;
		}
		Interest other = (Interest) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Interest [id=" + id + ", title=" + title + "]";
	}

}
