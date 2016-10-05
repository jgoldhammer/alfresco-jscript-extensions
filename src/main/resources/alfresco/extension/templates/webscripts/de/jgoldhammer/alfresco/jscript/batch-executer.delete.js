function main() {
	var jobId = url.templateArgs.jobId;
	if (!jobId) {
		status.code = 400;
		status.message = "jobId must be specified";
		status.redirect = true;
	}
	var canceled = batchExecuter.cancelJob(jobId);
	status.code = canceled ? 200 : 204;
	if (!canceled) {
		status.message = "Job " + jobId + " already finished or was canceled";
	}
	var location = "" + url.service;
	location = location.replace(/\/jobs.*/, '/jobs') + "?canceledJobId=" + jobId + "&canceled=" + canceled;
	status.location = location;

	model.jobId = jobId;
	model.canceled = canceled;
}

main();