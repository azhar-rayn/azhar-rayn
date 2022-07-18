import { Router } from 'express'
import { JobService } from '../services';

const JobRouter = Router();
const service: JobService = new JobService();

JobRouter.get('/', async (req, res) => {
  const result = await service.GetAllJobs()
  res.json(result)
})

JobRouter.get('/:jobID', async (req, res) => {
  const { jobID } = req.params
  const result = await service.GetJob(jobID)
  res.json(result)
})

JobRouter.put('/:jobID', async (req, res) => {
  const { title, type, classificationID, experience, address, education, responsibilities, gender, minSalary, maxSalary, startTime, endTime, positions, benefits, tags, description } = req.body
  const { jobID } = req.params

  try {
    const result = await service.UpdateJob(jobID, title, type, classificationID, experience, address, education, responsibilities, gender, minSalary, maxSalary, startTime, endTime, positions, benefits, tags, description)
    res.json(result)
  }
  catch (e) {

  } 
})

JobRouter.post('/', async (req, res) => {
  const { companyID, title, type, classificationID, experience, address, education, responsibilities, gender, minSalary, maxSalary, startTime, endTime, positions, benefits, tags, description } = req.body

  const result = await service.AddJob(companyID, title, type, classificationID, experience, address, education, responsibilities, gender, minSalary, maxSalary, startTime, endTime, positions, benefits, tags, description)
  res.json(result)
})

export default JobRouter;
