import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Resume, User } from '../entity'

export type ResumeType = 'Document' | 'Video' | undefined

export class ResumeRepository {

  repo: Repository<Resume>
  user: Repository<User>


  constructor() {
    this.repo = AppDataSource.getRepository(Resume);
    this.user = AppDataSource.getRepository(User);
  }

  //#######################################################################
  async AddResume(userID: string, resume: string, title: string, type: ResumeType): Promise<Resume> {


    try {

      const res = await this.repo.save({ userID, resume, title, type });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async ListResume(userID: string): Promise<Resume[]> {

    try {

      const res = await this.user.findOne({
        where: {
          ID: userID
        },
        relations: {
          Resume: true
        }
      })

      return res?.Resume as Resume[]
    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async ListResumeByType(userID: string, type: ResumeType): Promise<Resume[]> {

    try {
      const res = await this.user.findOne({
        where: {
          ID: userID,
          Resume: { type }
        },
        relations: {
          Resume: true
        }
      })
      return res?.Resume as Resume[]
    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }
}

