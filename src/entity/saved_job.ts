import { Entity, PrimaryGeneratedColumn, Column, ManyToOne } from "typeorm"
import { Job } from "./job";
import { User } from "./user";
@Entity()
export class Saved_Job {

  @PrimaryGeneratedColumn("uuid")
  ID!: string;

  @Column("uuid")
  userID!: string

  @Column("uuid")
  jobID!: string

  @ManyToOne(() => Job, job => job.SavedJob)
  Job!: Job;

  @ManyToOne(() => User, user => user.SavedJob)
  User!: User;
}
