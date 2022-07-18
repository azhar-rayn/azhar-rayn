import { Entity, PrimaryGeneratedColumn, Column, OneToOne, OneToMany, JoinColumn, ManyToOne } from "typeorm"
import { Address } from "./address";
import { Industry } from "./industry";
import { Job } from "./job";

@Entity()
export class Company {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column({ unique: true })
  name?: string;

  @Column("uuid")
  industryID: string

  @Column("uuid")
  addressID: string

  @OneToOne(() => Address)
  @JoinColumn()
  Address: Address;

  @Column({ unique: true })
  email?: string;

  @Column()
  contactPersonName?: string;

  @Column()
  jobDesignation?: string;

  @Column({ unique: true })
  phone?: number;

  @Column()
  logo?: string;

  @OneToMany(() => Job, Job => Job.Company)
  Jobs!: Job[];


  @ManyToOne(() => Industry, Industry => Industry.Companies)
  Industry: Industry;
}
