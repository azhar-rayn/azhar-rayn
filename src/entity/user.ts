
import { Certificate } from "./certificate";
import { Entity, PrimaryGeneratedColumn, Column, OneToMany, JoinColumn } from "typeorm"
import { Candidate_Experience } from "./candidate_experience";
import { Job_Application } from "./job_application";
import { Saved_Job } from "./saved_job";
import { UserSkill } from "./user_skill";
import { Resume } from "./resume";

@Entity()
export class User {

  @PrimaryGeneratedColumn("uuid")
  ID?: string;
  @Column({ unique: true })
  email!: string;
  @Column({ unique: true })
  phone!: string;
  @Column()
  password?: string;
  @Column({ nullable: true })
  source?: string;


  @OneToMany(() => Saved_Job, SavedJob => SavedJob.User)
  SavedJob?: Saved_Job[];

  @OneToMany(() => Job_Application, JobApplication => JobApplication.User)
  JobApplication?: Job_Application[];

  @OneToMany(() => Candidate_Experience, CandidateExperience => CandidateExperience.User)
  CandidateExperience?: Candidate_Experience[];

  @OneToMany(() => UserSkill, userSkill => userSkill.User)
  Skill?: UserSkill[];

  @OneToMany(() => Certificate, Certificate => Certificate.User)
  Certificate?: Certificate[];

  @OneToMany(() => Resume, Resume => Resume.User)
  Resume?: Resume[];
}