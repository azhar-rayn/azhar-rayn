import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository, Timestamp } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Saved_Job, User } from '../entity'


export class SavedJobRepository {

  repo: Repository<Saved_Job>
  user: Repository<User>

  constructor() {
    this.repo = AppDataSource.getRepository(Saved_Job);
    this.user = AppDataSource.getRepository(User);
  }

  //#######################################################################
  async DeleteSavedJob(savedJobID: string): Promise<Saved_Job> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: savedJobID })
        .execute();

      return result.raw;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetSavedJob(userID: string): Promise<Saved_Job[]> {

    try {

      const result = await this.user.findOne({ where: { ID: userID }, relations: { SavedJob: { Job: true } } })
      return result?.SavedJob as Saved_Job[];

    }
    catch (err) {

      console.log(err)
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddSavedJob(jobID: string, userID: string): Promise<Saved_Job> {

    try {

      const result = await this.repo.save({
        jobID,
        userID
      });

      return result;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

}

