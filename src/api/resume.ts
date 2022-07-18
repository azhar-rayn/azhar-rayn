import { Router } from 'express'
import { ResumeType } from '../repository/resume-repo';
import { ResumeService } from '../services';

const ResumeRouter = Router();
const service: ResumeService = new ResumeService();

ResumeRouter.get('/list/:userID', async (req, res) => {
  const { userID } = req.params;
  const type: ResumeType = req.query.type as ResumeType;

  const result = await service.ListResume(userID, type)
  res.json(result)
})

ResumeRouter.post('/:userID', async (req, res) => {

  const { resume, title, type } = req.body;
  const { userID } = req.params;

  const result = await service.AddResume(userID, resume, title, type)
  res.json(result)
})


export default ResumeRouter;
