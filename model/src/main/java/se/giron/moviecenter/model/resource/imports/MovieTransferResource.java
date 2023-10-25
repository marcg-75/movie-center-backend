package se.giron.moviecenter.model.resource.imports;

import se.giron.moviecenter.model.resource.MovieResource;

import java.util.Date;
import java.util.Objects;

public class MovieTransferResource {

	MovieResource movie;

	String fileName;
	
	Date filedate;
	
	MovieImportStatus status;
	
	String statusDescription;

	private Exception failure;
	
	Date importDate;

	Integer countMovies;
	long newMovies;
	long updatedMovies;

	public MovieResource getMovie() {
		return movie;
	}

	public MovieTransferResource setMovie(MovieResource movie) {
		this.movie = movie;
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public MovieTransferResource setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public Date getFiledate() {
		return filedate;
	}

	public MovieTransferResource setFiledate(Date filedate) {
		this.filedate = filedate;
		return this;
	}

    public MovieImportStatus getStatus() {
        return status;
    }

    public MovieTransferResource setStatus(MovieImportStatus status) {
        this.status = status;
        return this;
    }

    public String getStatusDescription() {
		return statusDescription;
	}

	public MovieTransferResource setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
		return this;
	}

	public Exception getFailure() {
		return failure;
	}

	public MovieTransferResource setFailure(Exception failure) {
		this.failure = failure;
		return this;
	}

	public Date getImportDate() {
		return importDate;
	}

	public MovieTransferResource setImportDate(Date importDate) {
		this.importDate = importDate;
		return this;
	}

	public Integer getCountMovies() {
		return countMovies;
	}

	public MovieTransferResource setCountMovies(Integer countMovies) {
		this.countMovies = countMovies;
		return this;
	}

    public long getNewMovies() {
		return newMovies;
	}

	public MovieTransferResource setNewMovies(long newMovies) {
		this.newMovies = newMovies;
		return this;
	}

	public long getUpdatedMovies() {
		return updatedMovies;
	}

	public MovieTransferResource setUpdatedMovies(long updatedMovies) {
		this.updatedMovies = updatedMovies;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MovieTransferResource that = (MovieTransferResource) o;
		return getNewMovies() == that.getNewMovies() &&
				getUpdatedMovies() == that.getUpdatedMovies() &&
				Objects.equals(getMovie(), that.getMovie()) &&
				Objects.equals(getFileName(), that.getFileName()) &&
				Objects.equals(getFiledate(), that.getFiledate()) &&
				getStatus() == that.getStatus() &&
				Objects.equals(getStatusDescription(), that.getStatusDescription()) &&
				Objects.equals(getImportDate(), that.getImportDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMovie(), getFileName(), getFiledate(), getStatus(), getStatusDescription(), getImportDate(), getNewMovies(), getUpdatedMovies());
	}
}
