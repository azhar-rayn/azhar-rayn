import { Router } from 'express'
import { InstituationService } from '../services';

const InstituationRouter = Router();
const service: InstituationService = new InstituationService();

InstituationRouter.get('/list', async (req, res) => {
  const result = await service.ListInstituation()
  res.json(result)
})

InstituationRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddInstituation(name)
  res.json(result)
})


export default InstituationRouter;
