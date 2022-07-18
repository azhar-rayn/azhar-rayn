import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository, Timestamp } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Job } from '../entity'


export class JobRepository {

  repo: Repository<Job>

  constructor() {
    this.repo = AppDataSource.getRepository(Job);

  }

  //#######################################################################
  async GetJob(jobID: string): Promise<Job> {

    try {

      const result = await this.repo.findOne({ where: { ID: jobID } });

      return result as Job

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async DeleteJob(jobID: string): Promise<Job> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: jobID })
        .execute();

      return result.raw;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async UpdateJob(
    jobID: string,
    title: string,
    type: string,
    classificationID: string,
    experience: number,
    education: string,
    responsibilities: string,
    gender: string,
    minSalary: number,
    maxSalary: number,
    startTime: Date | undefined,
    endTime: Date | undefined,
    positions: number,
    benefits: string,
    tags: string,
    description: string
  ): Promise<Job> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .update()
        .set({
          classificationID,
          description,
          education,
          endTime,
          experience,
          gender,
          maxSalary,
          minSalary,
          positions,
          responsibilities,
          startTime,
          tags,
          title,
          type,
          benefits
        })
        .returning("*")
        .where("ID = :ID", { ID: jobID })
        .execute()

      return result.raw

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddJob(
    companyID: string,
    title: string,
    type: string,
    classificationID: string,
    experience: number,
    addressID: string,
    education: string,
    responsibilities: string,
    gender: string,
    minSalary: number,
    maxSalary: number,
    startTime: Date | undefined,
    endTime: Date | undefined,
    positions: number,
    benefits: string,
    tags: string,
    description: string
  ): Promise<Job> {

    try {

      const result = await this.repo.save({
        companyID,
        classificationID,
        description,
        education,
        endTime,
        experience,
        gender,
        maxSalary,
        minSalary,
        positions,
        responsibilities,
        startTime,
        tags,
        title,
        type,
        benefits,
        addressID,
        createdAt: new Date()
      })

      return result

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetAllJobs(): Promise<Job[]> {

    try {

      const result = await this.repo.find({
        order: {
          createdAt: 'DESC'
        }
      })

      return result

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}

