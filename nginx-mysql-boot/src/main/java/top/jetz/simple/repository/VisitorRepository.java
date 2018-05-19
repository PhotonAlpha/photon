package top.jetz.simple.repository;

import org.springframework.data.repository.CrudRepository;

import top.jetz.simple.entity.Visitor;

public interface VisitorRepository extends CrudRepository<Visitor, Long>{
    Visitor findByIp(String ip);
}
