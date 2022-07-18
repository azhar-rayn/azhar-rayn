// All Business logic will be here

import { Resume } from "../entity";
import { ResumeRepository } from "../repository";
import { ResumeType } from "../repository/resume-repo";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class ResumeService {

  private repository: ResumeRepository

  constructor() {

    this.repository = new ResumeRepository();

  }


  //#######################################################################
  async AddResume(userID: string, resume: string, title: string, type: ResumeType): Promise<Resume> {

    try {

      return await this.repository.AddResume(userID, resume, title, type);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  } 


  //#######################################################################
  async ListResume(userID: string, type?: ResumeType): Promise<Resume[]> {

    try {

      if (type === 'Document' || type === 'Video')
        return await this.repository.ListResumeByType(userID, type);
      else if (type === undefined)
        return await this.repository.ListResume(userID);
      else
        throw new APIError('API Error', STATUS_CODES.BAD_REQUEST, 'Bad Request')

    } catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

}