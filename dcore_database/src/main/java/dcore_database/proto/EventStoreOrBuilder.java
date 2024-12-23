// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/resources/event_store.proto

package dcore_database.proto;

public interface EventStoreOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dcore_database.models.proto.EventStore)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string event_store_id = 1;</code>
   * @return The eventStoreId.
   */
  java.lang.String getEventStoreId();
  /**
   * <code>string event_store_id = 1;</code>
   * @return The bytes for eventStoreId.
   */
  com.google.protobuf.ByteString
      getEventStoreIdBytes();

  /**
   * <code>string event_name = 2;</code>
   * @return The eventName.
   */
  java.lang.String getEventName();
  /**
   * <code>string event_name = 2;</code>
   * @return The bytes for eventName.
   */
  com.google.protobuf.ByteString
      getEventNameBytes();

  /**
   * <code>string item_number = 3;</code>
   * @return The itemNumber.
   */
  java.lang.String getItemNumber();
  /**
   * <code>string item_number = 3;</code>
   * @return The bytes for itemNumber.
   */
  com.google.protobuf.ByteString
      getItemNumberBytes();

  /**
   * <code>string item_type = 4;</code>
   * @return The itemType.
   */
  java.lang.String getItemType();
  /**
   * <code>string item_type = 4;</code>
   * @return The bytes for itemType.
   */
  com.google.protobuf.ByteString
      getItemTypeBytes();

  /**
   * <code>int32 user_id = 5;</code>
   * @return The userId.
   */
  int getUserId();

  /**
   * <code>string user_login = 6;</code>
   * @return The userLogin.
   */
  java.lang.String getUserLogin();
  /**
   * <code>string user_login = 6;</code>
   * @return The bytes for userLogin.
   */
  com.google.protobuf.ByteString
      getUserLoginBytes();

  /**
   * <code>int32 node_id = 7;</code>
   * @return The nodeId.
   */
  int getNodeId();

  /**
   * <code>string node_code = 8;</code>
   * @return The nodeCode.
   */
  java.lang.String getNodeCode();
  /**
   * <code>string node_code = 8;</code>
   * @return The bytes for nodeCode.
   */
  com.google.protobuf.ByteString
      getNodeCodeBytes();

  /**
   * <code>.google.protobuf.Timestamp created_at = 9;</code>
   * @return Whether the createdAt field is set.
   */
  boolean hasCreatedAt();
  /**
   * <code>.google.protobuf.Timestamp created_at = 9;</code>
   * @return The createdAt.
   */
  com.google.protobuf.Timestamp getCreatedAt();
  /**
   * <code>.google.protobuf.Timestamp created_at = 9;</code>
   */
  com.google.protobuf.TimestampOrBuilder getCreatedAtOrBuilder();
}
