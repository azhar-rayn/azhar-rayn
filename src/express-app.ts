import { json, urlencoded, static as Static } from 'express';
import { AppDataSource } from './data-source';
import {
  ResumeRouter,
  UserRouter,
  CategoryRouter,
  ClassificationRouter,
  DegreeRouter,
  IndustryRouter,
  InstituationRouter,
  SkillRouter,
  UserProfileRouter,
  EducationFieldRouter,
  UserExperienceRouter,
  UserCertificateRouter,
  UserEducationRouter,
  UserSkillRouter,
  CompanyRouter,
  JobRouter,
  SavedJobRouter,
  JobApplicationRouter

} from './api';

export default async (app: any) => {

  await AppDataSource.initialize()
  app.use(json({ limit: '1mb' }));
  app.use(urlencoded({ extended: true, limit: '1mb' }));
  app.use(Static(__dirname + '/public'))


  app.use('/user', UserRouter)
  app.use('/resume', ResumeRouter)
  app.use('/category', CategoryRouter)
  app.use('/classification', ClassificationRouter)
  app.use('/degree', DegreeRouter)
  app.use('/industry', IndustryRouter)
  app.use('/instituation', InstituationRouter)
  app.use('/skill', SkillRouter)
  app.use('/profile', UserProfileRouter)
  app.use('/field', EducationFieldRouter)
  app.use('/experience', UserExperienceRouter)
  app.use('/certificate', UserCertificateRouter)
  app.use('/education', UserEducationRouter)
  app.use('/user_skill', UserSkillRouter)
  app.use('/company', CompanyRouter)
  app.use('/job', JobRouter)
  app.use('/job_application', JobApplicationRouter)
  app.use('/saved_job', SavedJobRouter)



}
