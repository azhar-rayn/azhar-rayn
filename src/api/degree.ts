import { Router } from 'express'
import { DegreeService } from '../services';

const DegreeRouter = Router();
const service: DegreeService = new DegreeService();

DegreeRouter.get('/list', async (req, res) => {
  const Degree = await service.ListDegree()
  res.json(Degree)
})

DegreeRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddDegree(name)
  res.json(result)
})


export default DegreeRouter;
