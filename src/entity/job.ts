import { Entity, PrimaryGeneratedColumn, Column, OneToMany, OneToOne, JoinColumn, ManyToOne } from "typeorm"
import { Address } from "./address";
import { Classification } from "./classification";
import { Company } from "./company";
import { Job_Application } from "./job_application";
import { Saved_Job } from "./saved_job";

@Entity()
export class Job {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column("uuid")
  companyID: string;

  @Column("uuid")
  addressID: string;

  @Column()
  title?: string;

  @Column()
  type?: string;

  @Column("uuid")
  classificationID: string;

  @Column()
  experience?: number;

  @OneToOne(() => Address)
  @JoinColumn()
  Address?: Address;

  @Column()
  education?: string;

  @Column()
  responsibilities?: string;

  @Column()
  gender?: string;

  @Column()
  minSalary?: number;

  @Column()
  maxSalary?: number;

  @Column("timestamp")
  startTime?: Date;

  @Column("timestamp")
  endTime?: Date;

  @Column()
  positions?: number;

  @Column()
  tags?: string;

  @Column()
  benefits?: string;

  @Column()
  description?: string;

  @Column("date")
  createdAt: Date

  @OneToMany(() => Saved_Job, SavedJob => SavedJob.Job)
  SavedJob!: Saved_Job[];

  @OneToMany(() => Job_Application, JobApplication => JobApplication.Job)
  JobApplication!: Job_Application[];

  @ManyToOne(() => Company, Company => Company.Jobs)
  Company: Company

  @ManyToOne(() => Classification, Classification => Classification.Jobs)
  Classification: Classification
}
