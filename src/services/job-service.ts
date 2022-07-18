import { Address, Job } from '../entity'
import { AddressRepository, JobRepository } from '../repository';
import { GenerateTime } from '../utils';
import { APIError, STATUS_CODES } from '../utils/app-errors';


export class JobService {

  repo: JobRepository
  addressRepo: AddressRepository


  constructor() {
    this.repo = new JobRepository()
    this.addressRepo = new AddressRepository()
  }

  //#######################################################################
  async GetJob(jobID: string): Promise<Job> {

    try {

      return await this.repo.GetJob(jobID)

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteJob(jobID: string): Promise<Job> {

    try {

      return await this.repo.DeleteJob(jobID)

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
    address: Address,
    education: string,
    responsibilities: string,
    gender: string,
    minSalary: number,
    maxSalary: number,
    startTime: string,
    endTime: string,
    positions: number,
    benefits: string,
    tags: string,
    description: string,
  ): Promise<Job> {

    try {

      await this.addressRepo.UpdateAddress(address)

      return await this.repo.UpdateJob(jobID, title, type, classificationID, experience, education, responsibilities, gender, minSalary, maxSalary, GenerateTime(startTime), GenerateTime(endTime), positions, benefits, tags, description)

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
    address: Address,
    education: string,
    responsibilities: string,
    gender: string,
    minSalary: number,
    maxSalary: number,
    startTime: string,
    endTime: string,
    positions: number,
    benefits: string,
    tags: string,
    description: string
  ): Promise<Job> {

    try {

      const { ID } = await this.addressRepo.AddAddress(address)

      return await this.repo.AddJob(companyID, title, type, classificationID, experience, ID, education, responsibilities, gender, minSalary, maxSalary, GenerateTime(startTime), GenerateTime(endTime), positions, benefits, tags, description)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetAllJobs(): Promise<Job[]> {

    try {

      return await this.repo.GetAllJobs()

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}

