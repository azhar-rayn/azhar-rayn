import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository, Timestamp } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Job, Job_Application, User } from '../entity'


export class JobApplicationRepository {

  repo: Repository<Job_Application>
  user: Repository<User>
  job: Repository<Job>

  constructor() {
    this.repo = AppDataSource.getRepository(Job_Application);
    this.user = AppDataSource.getRepository(User);
    this.job = AppDataSource.getRepository(Job);
  }

  //#######################################################################
  async GetJobApplication(jobApplicationID: string): Promise<Job_Application> {

    try {

      const result = await this.repo.findOne({ where: { ID: jobApplicationID } });

      return result as Job_Application

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetJobApplicationByUser(userID: string): Promise<Job_Application[]> {

    try {

      const result = await this.user.findOne({
        where: { ID: userID },
        relations: { JobApplication: { Resume: true, Job: true } }
      })

      return result?.JobApplication as Job_Application[]

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetJobApplicationByJob(jobID: string): Promise<Job_Application[]> {

    try {

      const result = await this.job.findOne({
        where: { ID: jobID },
        relations: { JobApplication: { User: true, Resume: true } }
      })

      return result?.JobApplication as Job_Application[]

    }
    catch (err) {

      console.log(err)
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async DeleteJobApplication(jobApplicationID: string): Promise<Job_Application> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: jobApplicationID })
        .execute();

      return result.raw;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddJobApplication(jobID: string, userID: string, resumeID: string): Promise<Job_Application> {

    try {

      const result = await this.repo.save({
        jobID,
        userID,
        applliedDate: new Date(),
        resumeID
      });

      return result;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

}

