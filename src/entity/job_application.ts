import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, OneToOne, JoinColumn } from "typeorm"
import { Job } from "./job";
import { Resume } from "./resume";
import { User } from "./user";

@Entity()
export class Job_Application {
  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column("uuid")
  userID: string;

  @Column("uuid")
  jobID: string;

  @Column("uuid", { nullable: true })
  resumeID?: string;

  @Column("date")
  applliedDate?: Date;

  @OneToOne(() => Resume)
  @JoinColumn()
  Resume?: Resume;

  @ManyToOne(() => Job, job => job.JobApplication)
  Job!: string;

  @ManyToOne(() => User, user => user.JobApplication)
  User!: string;

}
