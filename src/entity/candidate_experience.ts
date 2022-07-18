import { Entity, PrimaryGeneratedColumn, Column, OneToOne, JoinColumn, ManyToOne } from "typeorm"
import { Category } from "./category";
import { User } from "./user";

@Entity()
export class Candidate_Experience {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column("uuid")
  userID: string;

  @Column()
  title?: string;

  @Column("uuid")
  categoryID: string

  @Column()
  companyName?: string;

  @Column()
  city?: string;

  @Column()
  experience?: number;

  @ManyToOne(() => Category, category => category.CandidateExperience)
  Category!: Category;

  @ManyToOne(() => User, user => user.CandidateExperience)
  User!: string;
}
