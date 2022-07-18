import { Router } from 'express'
import { SavedJobService } from '../services';

const SavedJobRouter = Router();
const service: SavedJobService = new SavedJobService();

SavedJobRouter.post('/', async (req, res) => {

  const { jobID, userID } = req.body
  const result = await service.AddSavedJob(jobID, userID)
  res.json(result)

})

SavedJobRouter.delete('/:savedJobID', async (req, res) => {

  const { savedJobID } = req.params
  const result = await service.DeleteSavedJob(savedJobID)
  res.json(result)

})

SavedJobRouter.get('/', async (req, res) => {

  const { userID } = req.query
  const result = await service.GetSavedJob(userID as string)
  res.json(result)

})


export default SavedJobRouter;
