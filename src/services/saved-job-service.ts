import { Saved_Job } from '../entity'
import { SavedJobRepository } from '../repository';
import { APIError, STATUS_CODES } from '../utils/app-errors';


export class SavedJobService {

  repo: SavedJobRepository


  constructor() {
    this.repo = new SavedJobRepository()
  }

  //#######################################################################
  async AddSavedJob(jobID: string, userID: string): Promise<Saved_Job> {

    try {

      return await this.repo.AddSavedJob(jobID, userID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetSavedJob(userID: string): Promise<Saved_Job[]> {

    try {

      return await this.repo.GetSavedJob(userID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async DeleteSavedJob(savedJobID: string): Promise<Saved_Job> {

    try {

      return await this.repo.DeleteSavedJob(savedJobID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

}

