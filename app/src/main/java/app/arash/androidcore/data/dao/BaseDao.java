package app.arash.androidcore.data.dao;

import app.arash.androidcore.data.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Arash on 2018-01-27
 */
public interface BaseDao<ENTITY extends BaseEntity, PK extends Serializable> {

  PK create(ENTITY entity);

  void bulkInsert(List<ENTITY> list);

  void update(ENTITY entity);

  void delete(PK pk);

  ENTITY retrieve(PK pk);

  List<ENTITY> retrieveAll();

  void deleteAll();

  void deleteAll(String column, String condition);

}
