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

	Integer countTotal;
	Integer countSuccessful;
	Integer countIgnored;
	Integer countFailed;

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

	public Integer getCountTotal() {
		return countTotal;
	}

	public MovieTransferResource setCountTotal(Integer countTotal) {
		this.countTotal = countTotal;
		return this;
	}

    public Integer getCountSuccessful() {
		return countSuccessful;
	}

	public MovieTransferResource setCountSuccessful(Integer countSuccessful) {
		this.countSuccessful = countSuccessful;
		return this;
	}

	public Integer getCountIgnored() {
		return countIgnored;
	}

	public MovieTransferResource setCountIgnored(Integer countIgnored) {
		this.countIgnored = countIgnored;
		return this;
	}


	public Integer getCountFailed() {
		return countFailed;
	}

	public MovieTransferResource setCountFailed(Integer countFailed) {
		this.countFailed = countFailed;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MovieTransferResource that = (MovieTransferResource) o;
		return getCountSuccessful() == that.getCountSuccessful() &&
				getCountIgnored() == that.getCountIgnored() &&
				Objects.equals(getMovie(), that.getMovie()) &&
				Objects.equals(getFileName(), that.getFileName()) &&
				Objects.equals(getFiledate(), that.getFiledate()) &&
				getStatus() == that.getStatus() &&
				Objects.equals(getStatusDescription(), that.getStatusDescription()) &&
				Objects.equals(getImportDate(), that.getImportDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMovie(), getFileName(), getFiledate(), getStatus(), getStatusDescription(), getImportDate(), getCountSuccessful(), getCountIgnored());
	}
}
