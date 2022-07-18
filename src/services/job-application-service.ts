import { Job, Job_Application } from '../entity'
import { JobApplicationRepository } from '../repository';
import { APIError, STATUS_CODES } from '../utils/app-errors';


export class JobApplicationService {

  repo: JobApplicationRepository


  constructor() {
    this.repo = new JobApplicationRepository()
  }

  //#######################################################################
  async GetJobApplication(jobApplicationID: string): Promise<Job_Application> {

    try {

      return await this.repo.GetJobApplication(jobApplicationID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async JobApplication(userID: any, jobID: any): Promise<Job_Application[]> {

    try {

      let result: Job_Application[] = []
      if (userID) {
        return await this.repo.GetJobApplicationByUser(userID)
      }

      if (jobID) {
        return await this.repo.GetJobApplicationByJob(jobID)
      }

      return result

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddJobApplication(jobID: string, userID: string, resumeID: string): Promise<Job_Application> {

    try {

      return await this.repo.AddJobApplication(jobID, userID, resumeID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async DeleteJobApplication(jobApplicationID: string): Promise<Job_Application> {

    try {

      return await this.repo.DeleteJobApplication(jobApplicationID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

}

