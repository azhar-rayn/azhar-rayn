import { Entity, PrimaryGeneratedColumn, Column, ManyToOne } from "typeorm"
import { User } from "./user";

@Entity()
export class Certificate {
  @PrimaryGeneratedColumn("uuid")
  ID: string;
  
  @Column("uuid")
  userID: string;
  
  @Column()
  type?: string;
  
  @Column()
  topic?: string;
  
  @Column()
  instituation?: string;
  
  @Column("date")
  date?: Date;

  @ManyToOne(() => User, user => user.Certificate)
  User!: string;
}
