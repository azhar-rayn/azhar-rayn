import { Router } from 'express'
import { JobApplicationService } from '../services';

const JobApplicationRouter = Router();
const service: JobApplicationService = new JobApplicationService();

JobApplicationRouter.get('/:jobApplicationID', async (req, res) => {
  const { jobApplicationID } = req.params
  const result = await service.GetJobApplication(jobApplicationID)
  res.json(result)
})

JobApplicationRouter.post('/', async (req, res) => {

  const { jobID, userID, resumeID } = req.body
  const result = await service.AddJobApplication(jobID, userID, resumeID)
  res.json(result)

})


JobApplicationRouter.get('/', async (req, res) => {

  const { jobID, userID } = req.query
  const result = await service.JobApplication(userID, jobID)
  res.json(result)

})

JobApplicationRouter.delete('/:jobApplicationID', async (req, res) => {
  const { jobApplicationID } = req.params
  const result = await service.DeleteJobApplication(jobApplicationID)
  res.json(result)
})


export default JobApplicationRouter;
