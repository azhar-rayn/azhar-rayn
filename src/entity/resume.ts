import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm"
import { User } from "./user";

@Entity()
export class Resume {
  
  @PrimaryGeneratedColumn("uuid")
  ID: string;
  
  @Column("uuid")
  userID: string;

  @Column()
  resume: string;
  
  @Column({unique: true})
  title?: string;
  
  @Column()
  type?: string;

  @ManyToOne(() => User, user => user.Resume)
  User!: User;
}
